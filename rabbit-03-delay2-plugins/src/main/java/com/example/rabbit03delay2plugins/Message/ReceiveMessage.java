package com.example.rabbit03delay2plugins.Message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: keyanbin
 * @description: 接收消息
 * @create: 2023-11-22 17:35
 **/
@Component
@Slf4j
public class ReceiveMessage {

    @RabbitListener(queues = "queue.delay.2")
    public void receive(Message msg) {
        log.info("接收时间为：{}，消息为:{}", new Date(), new String(msg.getBody()));
    }
}
