package com.example.rabbit03delay2plugins.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author: keyanbin
 * @description: 使用延迟交换机插件实现延迟队列
 * @create: 2023-11-20 11:04
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "my")
public class RabbitDirectDelayConfig {
    // 延迟队列交换机
    private String exchangeDelayName;
    // 延迟队列
    private String queueDelayName;
    // 延迟队列路由键
    private String delayRoutingKey;

    // 自定义交换机
    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> arguments = Map.of("x-delayed-type", "direct");
        // CustomExchange 自定义交换机
        // 参数二：交换机类型 参数三：是否持久化 参数四：是否自动删除 参数五：参数
        return new CustomExchange(exchangeDelayName, "x-delayed-message", true, false, arguments);
    }

    // 延迟队列
    @Bean
    public Queue queueDelay() {
        return QueueBuilder.durable(queueDelayName).build();
    }

    @Bean
    public Binding bindingDelay(CustomExchange customExchange, Queue queueDelay) {
        // noargs() 不设置参数
        return BindingBuilder.bind(queueDelay).to(customExchange).with(delayRoutingKey).noargs();
    }
}
