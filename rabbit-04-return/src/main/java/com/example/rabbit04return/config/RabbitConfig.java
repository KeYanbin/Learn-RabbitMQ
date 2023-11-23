package com.example.rabbit04return.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-23 08:11
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties("my")
public class RabbitConfig {
    private String exchange;
    private String queue;
    private String routingKey;

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchange).build();
    }

    // 队列
    @Bean
    public Queue queue(){
        return QueueBuilder.durable(queue).build();
    }

    // 绑定
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

}
