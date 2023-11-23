package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 死信交换机, 设置消息属性过期模式
 * @create: 2023-11-21 16:24
 **/
@Configuration
public class RabbitDLXConfig2 {

    // 正常交换机
    public static final String NORMAL_NAME = "exchange.normal.2";
    // 正常队列
    public static final String NORMAL_QUE = "queue.normal.2";
    // 死信交换机
    public static final String DLX_NAME = "exchange.dlx.2";
    // 死信队列
    public static final String DLX_QUE = "queue.dlx2";
    // 死信路由键
    public static final String DLX_KEY = "dead2";
    // 正常路由键
    public static final String NORMAL_KEY = "normal2";

    // 交换机
    @Bean
    public DirectExchange normalExchange2() {
        return ExchangeBuilder.directExchange(NORMAL_NAME).build();
    }

    @Bean
    public Queue normalQueue2() {
        return QueueBuilder
                .durable(NORMAL_QUE)
                .maxLength(5) // 设置队列中的消息最大长度
                .deadLetterExchange(DLX_NAME) // 绑定死信交换机
                .deadLetterRoutingKey(DLX_KEY) // 指定死信交换机和死信队列的路由键，要和死信交换机的路由键一致,因为是直连交换机，所以路由键要一致
                .build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding normalBinding2(DirectExchange normalExchange2, Queue normalQueue2) {
        return BindingBuilder
                .bind(normalQueue2)
                .to(normalExchange2)
                .with(NORMAL_KEY);
    }

    // 死信交换机
    @Bean
    public DirectExchange dlxExchange2() {
        return ExchangeBuilder
                .directExchange(DLX_NAME)
                .build();
    }

    // 死信队列
    @Bean
    public Queue dlxQueue2() {
        return QueueBuilder
                .durable(DLX_QUE)
                .build();
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlxBinding2(DirectExchange dlxExchange2, Queue dlxQueue2) {
        return BindingBuilder
                .bind(dlxQueue2)
                .to(dlxExchange2)
                .with(DLX_KEY);
    }
}
