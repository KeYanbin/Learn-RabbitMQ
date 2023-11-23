package com.example.rabbit04confirm1.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @author: keyanbin
 * @description: 确认模式写法一 ，使用匿名内部类的方式
 * @create: 2023-11-23 08:31
 **/
@Slf4j
// @RestController
@RequestMapping("/confirm2")
public class ConfirmTestController2 {
    @Value("${my.exchange}")
    private String exchange;
    @Value("${my.routing-key}")
    private String routingKey;
    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * @PostConstruct 注解的方法将会在依赖注入完成后被自动调用
     * 初始化
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(
                new RabbitTemplate.ConfirmCallback() {
                    @Override
                    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                        if (ack) {
                            log.info("(确认模式) 消息正常发送：{}", correlationData);
                            return;
                        }
                        log.error("(确认模式) 消息发送异常 ：{}，异常原因：{}", correlationData, cause);
                    }
                }
        );
    }

    @GetMapping("/{mes}")
    public String sendMsg(@PathVariable String mes) {
        // 声明一个相关性数据，可以用于辅助确认本次请求的信息
        // 这里使用UUID作为相关性数据
        CorrelationData correlationData = new CorrelationData();
        String uuid = "ORDER_NUM_" + UUID.randomUUID();
        correlationData.setId(uuid);
        log.info("(确认模式)准备发送的信息：{} ， 交换机名称是 :{} , 相关数据信息：{} ", mes, routingKey, correlationData);
        rabbitTemplate.convertAndSend(exchange, routingKey, mes, correlationData);
        return "confirm模式发送消息成功";
    }

}
