package com.example.rabbit06idempotent.controller;


import com.example.rabbit06idempotent.vo.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-25 17:01
 **/
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("(确认模式) 消息正常发送：{}", correlationData);
                return;
            }
            log.error("(确认模式) 消息发送异常 ：{}，异常原因：{}", correlationData, cause);
        });

        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("(Return模式) 消息从交换机到队列失败，回调该方法");
            log.info("(Return模式) 消息主体 message : {}", returned.getMessage());
            log.info("(Return模式) 描述：{}", returned.getReplyText());
            log.info("(Return模式) 消息使用的交换机 exchange : {}", returned.getExchange());
            log.info("(Return模式) 消息使用的路由键 routing : {}", returned.getRoutingKey());
        });
    }

    @RequestMapping("/send")
    public String send() throws JsonProcessingException {
        Order order1 = Order.builder()
                .id("Order_123456")
                .OrderName("订单1")
                .Money(new BigDecimal(100))
                .createTime(new Date())
                .build();
        String strOrder1 = objectMapper.writeValueAsString(order1);
        Message message = MessageBuilder.withBody(strOrder1.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.idempotent", "routing.idempotent", message);
        return "send message" + strOrder1;
    }

    // 接收消息的方法
    @RabbitListener(queues = "${my.queue}")
    public void receive(Message message, Channel channel) throws IOException {
        // 获取消息的唯一标识
        long deliveryTag = channel.getChannelNumber();
        // 反序列化消息
        Order order = objectMapper.readValue(message.getBody(), Order.class);
        try {
            log.info("接收到的消息是：{}", order.toString());
            /*
             * 处理幂等性的方式：
             * 1. 通过redis的setnx命令来实现幂等性
             *
             */
            Boolean setResult = stringRedisTemplate.opsForValue().setIfAbsent("idempotent:" + order.getId(), order.getId());
            if (setResult) {
                log.info("幂等性校验通过，开始处理业务逻辑");
                // TODO 处理业务逻辑
            } else {
                log.info("重复消费消息，不处理业务逻辑");
            }
            // 手动确认消息 参数1：消息的唯一标识 参数2：是否开启多个消息同时确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("接收消息发生异常：{}", e.getMessage());
            try {
                // 参数1：消息的唯一标识 参数2：是否开启多个消息同时拒绝 参数3：是否重回队列
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
