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
public class RabbitDLXConfig4 {

    // 正常交换机
    public static final String NORMAL_NAME = "exchange.normal.3";
    // 正常队列
    public static final String NORMAL_QUE = "queue.normal.3";
    // 死信交换机
    public static final String DLX_NAME = "exchange.dlx.3";
    // 死信队列
    public static final String DLX_QUE = "queue.dlx3";
    // 死信路由键
    public static final String DLX_KEY = "dead3";
    // 正常路由键
    public static final String NORMAL_KEY = "normal3";

    // 交换机
    @Bean
    public DirectExchange normalExchange4() {
        return ExchangeBuilder.directExchange(NORMAL_NAME).build();
    }

    @Bean
    public Queue normalQueue4() {
        return QueueBuilder
                .durable(NORMAL_QUE)
                .deadLetterExchange(DLX_NAME) // 绑定死信交换机
                .deadLetterRoutingKey(DLX_KEY) // 指定死信交换机和死信队列的路由键，要和死信交换机的路由键一致,因为是直连交换机，所以路由键要一致
                .build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding normalBinding4(DirectExchange normalExchange4, Queue normalQueue4) {
        return BindingBuilder
                .bind(normalQueue4)
                .to(normalExchange4)
                .with(NORMAL_KEY);
    }

    // 死信交换机
    @Bean
    public DirectExchange dlxExchange4() {
        return ExchangeBuilder
                .directExchange(DLX_NAME)
                .build();
    }

    // 死信队列
    @Bean
    public Queue dlxQueue4() {
        return QueueBuilder
                .durable(DLX_QUE)
                .build();
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlxBinding4(DirectExchange dlxExchange4, Queue dlxQueue4) {
        return BindingBuilder
                .bind(dlxQueue4)
                .to(dlxExchange4)
                .with(DLX_KEY);
    }
}
