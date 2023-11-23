package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 直连交换机配置类
 * @create: 2023-11-20 11:04
 **/
@Configuration
public class RabbitDirectConfig {
    private static final String EXCHANGE_DIRECT = "exchange.direct";
    private static final String QUEUE_DIRECT_A = "queue.direct.A";
    private static final String QUEUE_DIRECT_B = "queue.direct.B";

    // 定义交换机
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).build();
    }

    // 定义队列
    @Bean
    public Queue directQueueA() {
        return new Queue(QUEUE_DIRECT_A);
    }

    @Bean
    public Queue directQueueB() {
        return new Queue(QUEUE_DIRECT_B);
    }

    // 绑定交换机和队列
    @Bean
    public Binding directBindingA(DirectExchange directExchange, Queue directQueueA) {
        // 将队列A绑定到交换机 并且指定routingKey 为error
        return BindingBuilder.bind(directQueueA).to(directExchange).with("error");
    }

    @Bean
    public Binding directBindingB(DirectExchange directExchange, Queue directQueueB) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("info");
    }

    @Bean
    public Binding directBindingB1(DirectExchange directExchange, Queue directQueueB) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("error");
    }

    @Bean
    public Binding directBindingB2(DirectExchange directExchange, Queue directQueueB) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("warning");
    }
}
