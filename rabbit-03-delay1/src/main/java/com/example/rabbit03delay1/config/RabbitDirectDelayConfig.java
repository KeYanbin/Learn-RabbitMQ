package com.example.rabbit03delay1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 延迟队列配置
 * @create: 2023-11-20 11:04
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "my")
public class RabbitDirectDelayConfig {
    // 延迟队列交换机
    private String exchangeName;
    // 延迟队列
    private String queueNormalName;
    // 死信队列
    private String queueDlxName;
    // 死信队列路由键
    private String dlxRoutingKey;
    // 延迟队列路由键
    private String normalRoutingKey;

    // 交换机
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    // 延迟队列
    @Bean
    public Queue queueNormal() {
        return QueueBuilder.durable(queueNormalName)
                .ttl(15000)
                .deadLetterExchange(exchangeName)
                .deadLetterRoutingKey(dlxRoutingKey)
                .build();
    }
    // 死信队列
    @Bean
    public Queue queueDlx() {
        return QueueBuilder.durable(queueDlxName).build();
    }

    // 绑定交换机和队列
    @Bean
    public Binding bindingNormal(DirectExchange directExchange, Queue queueNormal) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with(normalRoutingKey);
    }
    // 绑定死信队列
    @Bean
    public Binding bindingDlx(DirectExchange directExchange, Queue queueDlx) {
        return BindingBuilder.bind(queueDlx).to(directExchange).with(dlxRoutingKey);
    }
}
