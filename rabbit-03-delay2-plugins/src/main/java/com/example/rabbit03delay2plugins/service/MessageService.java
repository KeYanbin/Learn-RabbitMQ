package com.example.rabbit03delay2plugins.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${my.exchange-delay-name}")
    private String exchange;
    @Value("${my.delay-routing-key}")
    private String routingKey;
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMsg(String mes) {
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", 5000);
            Message message = MessageBuilder.withBody(mes.getBytes()).andProperties(messageProperties).build();
            log.info("延迟消息发送时间:{}", new Date());
            rabbitTemplate.convertAndSend(exchange, routingKey,message);
        }
        {
            mes =mes+"2";
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay", 25000);
            Message message = MessageBuilder.withBody(mes.getBytes()).andProperties(messageProperties).build();
            log.info("延迟消息发送时间:{}", new Date());
            rabbitTemplate.convertAndSend(exchange, routingKey,message);
        }

    }
}
