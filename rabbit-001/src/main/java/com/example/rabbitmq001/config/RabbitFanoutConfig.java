package com.example.rabbitmq001.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: RabbitMQ扇形交换机配置类
 * @create: 2023-11-20 10:10
 **/
@Configuration
public class RabbitFanoutConfig {
    // rabbitmq
    // 三部曲
    // 1 定义交换机
    // 2 定义队列
    // 3 绑定交换机和队列

    /**
     * 定义交换机
     *
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange.fanout");
    }

    /**
     * 定义队列
     *
     * @return Queue
     */
    @Bean
    public Queue queueA() {
        return new Queue("queue.fanout.A");
    }

    @Bean
    public Queue queueB() {
        return new Queue("queue.fanout.B");
    }

    /**
     * 将队列绑定到交换机
     *
     * @param fanoutExchange 交换机
     * @param queueA         队列
     * @return Binding
     */
    @Bean
    public Binding bindingA(FanoutExchange fanoutExchange, Queue queueA) {
        // 将队列A绑定到交换机
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    public Binding bindingB(FanoutExchange fanoutExchange, Queue queueB) {
        // 将队列B绑定到交换机
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
}
