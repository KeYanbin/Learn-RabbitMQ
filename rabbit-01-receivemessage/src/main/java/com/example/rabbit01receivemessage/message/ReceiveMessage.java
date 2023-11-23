package com.example.rabbit01receivemessage.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-20 10:52
 **/
@Slf4j
@Component
public class ReceiveMessage {

    @RabbitListener(queues = {"queue.fanout.A", "queue.fanout.B"})
    public void receiveFanoutMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }

    @RabbitListener(queues = {"queue.direct.A", "queue.direct.B"})
    public void receiveDirectMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }

    @RabbitListener(queues = {"queue.topic.A", "queue.topic.B"})
    public void receiveTopicMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }

    @RabbitListener(queues = {"queue.headers.A", "queue.headers.B"})
    public void receiveHeadersMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }

    @RabbitListener(queues = {"queue.ttl.A", "queue.ttl"})
    public void receiveTtlMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }

    @RabbitListener(queues = {"queue.dlx1",})
    public void receiveDLXMessage(Message message) {
        // 获取消息体
        byte[] body = message.getBody();
        // 获取了消息的属性，包括消息的头部信息、消息ID、时间戳等。
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取消费的队列名称
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取交换机名称
        String receivedExchange = messageProperties.getReceivedExchange();
        log.info("接收到的消息：{} . 消息队列 ：{} , 交换机名称：{}", new String(body), consumerQueue, receivedExchange);
    }
}
