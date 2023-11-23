package com.example.rabbitmq001.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-20 10:26
 **/
@Slf4j
@Service
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 扇形交换机发送消息
     */
    public void sendMessageFanout(String mes) {
        Message message = new Message((mes + "扇形交换机").getBytes());
        rabbitTemplate.convertAndSend("exchange.fanout", "", message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    /**
     * 直连交换机发送消息
     */
    public void sendMessageDirect(String routingKey, String mes) {
        Message message = MessageBuilder.withBody(mes.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.direct", routingKey, message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    /**
     * 主题交换机发送消息
     */
    public void sendMessageTopic(String routingKey, String mes) {
        Message message = MessageBuilder.withBody(mes.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.topic", routingKey, message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    public void sendMessageHeaders(String type, String status, String msg) {
        // 创建消息属性
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息头
        messageProperties.setHeader("type", type);
        messageProperties.setHeader("status", status);
        // 创建消息
        Message message = MessageBuilder.withBody(msg.getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.headers", "", message);
    }

    public void sendMessageTtl(String mes) {
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息过期时间 单位毫秒
        messageProperties.setExpiration("15000");
        Message message = MessageBuilder.withBody(mes.getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.direct", "info", message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    public void sendMessageTtlA(String mes) {
        Message message = MessageBuilder.withBody(mes.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.ttl", "ttlA", message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    public void sendMessageDlx(String mes) {
        Message message = MessageBuilder.withBody(mes.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.normal.1", "normal", message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    public void sendMessageDlx2(String mes) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("15000");
        Message message = MessageBuilder
                .withBody(mes.getBytes())
                .andProperties(messageProperties)
                .build();
        rabbitTemplate.convertAndSend("exchange.normal.2", "normal2", message);
        log.info("发送消息成功,时间为：{}", new Date());
    }

    public void sendMessageDlx3(String mes) {
        for (int i = 1; i <= 8; i++) {
            mes = mes + i;
            Message message = MessageBuilder
                    .withBody(mes.getBytes())
                    .build();
            rabbitTemplate.convertAndSend("exchange.normal.2", "normal2", message);
        }
        log.info("发送消息成功,时间为：{}", new Date());
    }
    public void sendMessageDlx4(String mes) {
        Message message = MessageBuilder
                .withBody(mes.getBytes())
                .build();
        rabbitTemplate.convertAndSend("exchange.normal.3", "normal3", message);
    }
}
