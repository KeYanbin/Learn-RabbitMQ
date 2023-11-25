package com.example.rabbit5alternateexchange.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: keyanbin
 * @description: 确认模式写法一 ，单独写一个类继承RabbitTemplate.ConfirmCallback接口
 * @create: 2023-11-23 08:31
 **/
@Slf4j
@RestController
@RequestMapping("/alternate")
public class ReturnTestController1 {
    @Value("${my.exchangeNormalName}")
    private String exchange;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/{mes}")
    public String sendMsg(@PathVariable String mes) {
        log.info("(确认模式)准备发送的信息：{} ， 交换机名称是 :{} ", mes, exchange);
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息持久化 rabbitmq重启后消息不会丢失
        // 默认是MessageDeliveryMode.PERSISTENT，即默认是持久化的
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(mes.getBytes(), messageProperties);

        rabbitTemplate.convertAndSend(exchange, "info", message);
        return "alternate模式发送消息成功";
    }

}
