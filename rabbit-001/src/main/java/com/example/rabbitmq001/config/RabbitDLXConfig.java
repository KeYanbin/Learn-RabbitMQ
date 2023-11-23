package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 死信交换机
 * @create: 2023-11-21 16:24
 **/
@Configuration
public class RabbitDLXConfig {

    // 正常交换机
    public static final String NORMAL_NAME = "exchange.normal.1";
    // 正常队列
    public static final String NORMAL_QUE = "queue.normal.1";
    // 死信交换机
    public static final String DLX_NAME = "exchange.dlx.1";
    // 死信队列
    public static final String DLX_QUE = "queue.dlx1";
    // 死信路由键
    public static final String DLX_KEY = "dead";
    // 正常路由键
    public static final String NORMAL_KEY = "normal";

    // 交换机
    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange(NORMAL_NAME).build();
    }

    @Bean
    public Queue normalQueue() {
        return QueueBuilder
                .durable(NORMAL_QUE)
                .ttl(15000) // 设置队列中的消息过期时间
                .deadLetterExchange(DLX_NAME) // 绑定死信交换机
                .deadLetterRoutingKey(DLX_KEY) // 指定死信交换机和死信队列的路由键，要和死信交换机的路由键一致,因为是直连交换机，所以路由键要一致
                .build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding normalBinding(DirectExchange normalExchange, Queue normalQueue) {
        return BindingBuilder
                .bind(normalQueue)
                .to(normalExchange)
                .with(NORMAL_KEY);
    }

    // 死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return ExchangeBuilder
                .directExchange(DLX_NAME)
                .build();
    }

    // 死信队列
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder
                .durable(DLX_QUE)
                .build();
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlxBinding(DirectExchange dlxExchange, Queue dlxQueue) {
        return BindingBuilder
                .bind(dlxQueue)
                .to(dlxExchange)
                .with(DLX_KEY);
    }
}
