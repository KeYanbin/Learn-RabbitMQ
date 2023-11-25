package com.example.rabbit5alternateexchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description: 备用交换机
 * @create: 2023-11-23 18:46
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "my")
public class RabbitConfig {

    private String exchangeNormalName;
    private String exchangeAlternateName;
    private String queueNormalName;
    private String queueAlternateName;

    // 正常直连交换机
    @Bean
    public DirectExchange exchangeNormal() {
        return ExchangeBuilder
                .directExchange(exchangeNormalName)
                .alternate(exchangeAlternateName)// 绑定备用交换机
                .build();
    }

    // 正常队列
    @Bean
    public Queue queueNormal() {
        return QueueBuilder
                .durable(queueNormalName)
                .build();
    }

    // 正常队列绑定正常交换机
    @Bean
    public Binding bindingNormal(DirectExchange exchangeNormal, Queue queueNormal) {
        return BindingBuilder
                .bind(queueNormal)
                .to(exchangeNormal)
                .with("info");
    }

    // 备用队列
    @Bean
    public Queue queueAlternate() {
        return QueueBuilder
                .durable(queueAlternateName)
                .build();
    }

    // 备用交换机
    @Bean
    public FanoutExchange exchangeAlternate() {
        return ExchangeBuilder
                .fanoutExchange(exchangeAlternateName)
                .build();
    }

    // 备用队列绑定备用交换机
    @Bean
    public Binding bindingAlternate(FanoutExchange exchangeAlternate, Queue queueAlternate) {
        return BindingBuilder
                .bind(queueAlternate)
                .to(exchangeAlternate);
    }
}
