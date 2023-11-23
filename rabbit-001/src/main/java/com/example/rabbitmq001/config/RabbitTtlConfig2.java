package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 设置消息过期 方式二：通过队列属性设置过期时间
 * @create: 2023-11-20 22:28
 **/
@Configuration
public class RabbitTtlConfig2 {

    private static final String EXCHANGE_TTL = "exchange.ttl";
    private static final String QUEUE_TTL = "queue.ttl.A";

    // 交换机
    @Bean
    public DirectExchange ttlExchange2() {
        return ExchangeBuilder.directExchange(EXCHANGE_TTL).build();
    }

    // 消息队列
    @Bean
    public Queue ttlQueue2() {
        // // 方式一：
        // Map<String, Object> arguments = new HashMap<>();
        // arguments.put("x-message-ttl", 15000);
        // return new Queue(QUEUE_TTL, true, false, false, arguments);

        // 方式二：设置队列的过期时间 15s
        return QueueBuilder
                .durable(QUEUE_TTL)
                .ttl(15000)
                .build();
    }

    // 绑定消息队列和交换机
    @Bean
    public Binding ttlBinding2(DirectExchange ttlExchange2, Queue ttlQueue2) {
        return BindingBuilder.bind(ttlQueue2).to(ttlExchange2).with("ttlA");
    }
}
