package com.example.rabbitmq001.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 主题交换机配置
 * @create: 2023-11-20 17:55
 **/
@Configuration
public class RabbitTopicConfig {
    private static final String TOPIC_QUEUE_A = "queue.topic.A";
    private static final String TOPIC_QUEUE_B = "queue.topic.B";
    private static final String EXCHANGE_DIRECT = "exchange.topic";

    // 创建交换机
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_DIRECT).build();
    }

    // 创建队列
    @Bean
    public Queue topicQueueA() {
        // 队列持久化
        return QueueBuilder.durable(TOPIC_QUEUE_A).build();
    }

    @Bean
    public Queue topicQueueB() {
        return QueueBuilder.durable(TOPIC_QUEUE_B).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding topicBindingA(TopicExchange topicExchange, Queue topicQueueA) {
        // * 代表一个单词 -> 必须出现的且只有一个  xxx.orange.xxx
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("*.orange.*");
    }

    @Bean
    public Binding topicBindingB(TopicExchange topicExchange, Queue topicQueueB) {
        // xx.xx.rabbit -> 必须出现的且只有两个
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("*.*.rabbit");
    }

    @Bean
    public Binding topicBindingB1(TopicExchange topicExchange, Queue topicQueueB) {
        // # 代表零个或多个单词 即lazy.任意层级层次 lazy.xxx.xxx.xxx
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("lazy.#");
    }
}
