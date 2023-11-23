package com.example.rabbitmq002manual.message;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-22 00:57
 **/
@Slf4j
@Component
public class ReceiveMessage {
    @RabbitListener(queues = {"queue.normal.3",})
    public void receiveDLXMessage(Message message, Channel channel) {
        // 获取消息体
        byte[] body = message.getBody();
        String result = new String(body);
        // 获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消息传递的唯一标签
        long deliveryTag = messageProperties.getDeliveryTag();
        log.info("接收到的消息：{}", result);

        try {
            // int a = 1/0;
            // 手动确认消息 参数1：消息的唯一标签  参数2：是否批量确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            try {
                // // 手动拒绝消息 参数1：消息的唯一标签  参数2：是否批量确认  参数3：是否重新入队
                channel.basicNack(deliveryTag, false, false);
                // 拒绝消息 参数1：消息的唯一标签  参数2： 是否重新入队
                // channel.basicReject(deliveryTag, false);
                log.error("遇到异常，拒绝消息：{}", e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @RabbitListener(queues = {"queue.dlx3",})
    public void receiveDLXMessage2(Message message, Channel channel) {
        // 获取消息体
        byte[] body = message.getBody();
        String result = new String(body);
        // 获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消息传递的唯一标签
        long deliveryTag = messageProperties.getDeliveryTag();
        log.info("接收到的消息2：{}", result);

        try {
            // 手动确认消息 参数1：消息的唯一标签  参数2：是否批量确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            try {
                // 手动拒绝消息 参数1：消息的唯一标签  参数2：是否批量确认  参数3：是否重新入队
                channel.basicNack(deliveryTag, false, true);
                log.error("遇到异常，拒绝消息：{}", e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
