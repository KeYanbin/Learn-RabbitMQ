package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: keyanbin
 * @description: 头部交换机
 * @create: 2023-11-20 20:16
 **/
@Configuration
public class RabbitHeadersConfig {
    private static final String HEADERS_QUEUE_A = "queue.headers.A";
    private static final String HEADERS_QUEUE_B = "queue.headers.B";
    private static final String EXCHANGE_HEADERS = "exchange.headers";

    // 创建交换机
    @Bean
    public HeadersExchange headersExchange() {
        return ExchangeBuilder.headersExchange(EXCHANGE_HEADERS).build();
    }

    // 创建队列
    @Bean
    public Queue headersQueueA() {
        // 队列持久化
        return QueueBuilder.durable(HEADERS_QUEUE_A).build();
    }

    @Bean
    public Queue headersQueueB() {
        return QueueBuilder.durable(HEADERS_QUEUE_B).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding headersBindingA(HeadersExchange headersExchange, Queue headersQueueA) {
        Map<String, Object> headerValues = new HashMap<>();
        headerValues.put("type", "m");
        headerValues.put("status", "1");
        // whereAll 表示全部匹配
        return BindingBuilder.bind(headersQueueA).to(headersExchange).whereAll(headerValues).match();
    }
    @Bean
    public Binding headersBindingB(HeadersExchange headersExchange, Queue headersQueueB) {
        Map<String, Object> headerValues = new HashMap<>();
        headerValues.put("type", "s");
        headerValues.put("status", "2");
        // whereAny 表示部分匹配
        return BindingBuilder.bind(headersQueueB).to(headersExchange).whereAny(headerValues).match();
    }

}
