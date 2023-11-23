package com.example.rabbit04return.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: Return模式实现方式一
 * @create: 2023-11-23 10:40
 **/
@Slf4j
@Configuration
public class MyReturnCallback implements RabbitTemplate.ReturnsCallback {

    /**
     * 当消息从交换机 没有正确地 到达队列，则会触发该方法
     * 如果消息从交换机 正确地 到达队列了，那么就不会触发该方法
     *
     * @param returned 消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息从交换机到队列失败，回调该方法");
        log.info("消息主体 message : {}", returned.getMessage());
        log.info("描述：{}", returned.getReplyText());
        log.info("消息使用的交换机 exchange : {}", returned.getExchange());
        log.info("消息使用的路由键 routing : {}", returned.getRoutingKey());
    }
}
