package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 设置消息过期,方式一：通过消息属性设置过期时间
 * @create: 2023-11-20 22:28
 **/
@Configuration
public class RabbitTtlConfig {

    private static final String EXCHANGE_TTL = "exchange.ttl";
    private static final String QUEUE_TTL = "queue.ttl";

    // 交换机
    @Bean
    public DirectExchange ttlExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_TTL).build();
    }

    // 消息队列
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder.durable(QUEUE_TTL).build();
    }

    // 绑定消息队列和交换机
    @Bean
    public Binding ttlBinding(DirectExchange ttlExchange, Queue ttlQueue) {
        return BindingBuilder.bind(ttlQueue).to(ttlExchange).with("ttl");
    }
}
