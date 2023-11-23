package com.example.rabbit04confirm1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-23 08:28
 **/
@Slf4j
@Configuration
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {
    /**
     * @param correlationData 相关数据
     * @param ack             确认情况
     * @param cause           原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("(确认模式) 消息正常发送：{}", correlationData);
            return;
        }
        log.error("(确认模式) 消息发送异常 ：{}，异常原因：{}", correlationData, cause);
    }
}
