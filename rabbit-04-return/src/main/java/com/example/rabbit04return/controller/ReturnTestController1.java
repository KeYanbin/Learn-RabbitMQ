package com.example.rabbit04return.controller;

import com.example.rabbit04return.config.MyReturnCallback;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: keyanbin
 * @description: 确认模式写法一 ，单独写一个类继承RabbitTemplate.ConfirmCallback接口
 * @create: 2023-11-23 08:31
 **/
@Slf4j
@RestController
@RequestMapping("/return1")
public class ReturnTestController1 {
    @Value("${my.exchange}")
    private String exchange;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MyReturnCallback myReturnCallback;

    @PostConstruct
    public void init() {
        // 设置确认回调函数
        // 外部类写法
        // rabbitTemplate.setReturnsCallback(myReturnCallback);

        // 匿名内部类写法
        // rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
        //     @Override
        //     public void returnedMessage(org.springframework.amqp.core.ReturnedMessage returned) {
        //         log.info("消息从交换机到队列失败，回调该方法");
        //         log.info("消息主体 message : {}", returned.getMessage());
        //         log.info("描述：{}", returned.getReplyText());
        //         log.info("消息使用的交换机 exchange : {}", returned.getExchange());
        //         log.info("消息使用的路由键 routing : {}", returned.getRoutingKey());
        //     }
        // });
        // lambda表达式写法
        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("消息从交换机到队列失败，回调该方法");
            log.info("消息主体 message : {}", returned.getMessage());
            log.info("描述：{}", returned.getReplyText());
            log.info("消息使用的交换机 exchange : {}", returned.getExchange());
            log.info("消息使用的路由键 routing : {}", returned.getRoutingKey());
        });

        log.info("(确认模式)成功配置回调方法");
    }

    @GetMapping("/{mes}/{key}")
    public String sendMsg(@PathVariable String mes, @PathVariable String key) {
        // 声明一个相关性数据，可以用于辅助确认本次请求的信息
        // 这里使用UUID作为相关性数据
        CorrelationData correlationData = new CorrelationData();
        String uuid = "ORDER_NUM_" + UUID.randomUUID();
        correlationData.setId(uuid);
        log.info("(确认模式)准备发送的信息：{} ， 交换机名称是 :{} , 相关数据信息：{} ", mes, key, correlationData);
        rabbitTemplate.convertAndSend(exchange, key, mes, correlationData);
        return "return模式发送消息成功";
    }

}
