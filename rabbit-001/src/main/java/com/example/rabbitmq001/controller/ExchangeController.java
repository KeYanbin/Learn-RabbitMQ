package com.example.rabbitmq001.controller;

import com.example.rabbitmq001.service.MessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-20 17:34
 **/
@Slf4j
@RestController
public class ExchangeController {
    @Resource
    private MessageService messageService;

    @GetMapping("/fanout/{mes}")
    public String fanout(@PathVariable String mes) {
        log.info("扇形交换机->mes: {}", mes);
        messageService.sendMessageFanout(mes);
        return "扇形交换机,发送成功";
    }

    @GetMapping("/direct/{key}/{mes}")
    public String direct(@PathVariable("key") String routingKey, @PathVariable String mes) {
        log.info("直连交换机->routingKey: {},{}", routingKey, mes);
        messageService.sendMessageDirect(routingKey, mes);
        return "直连交换机,发送成功";
    }

    @GetMapping("/topic/{key}/{mes}")
    public String topic(@PathVariable("key") String routingKey, @PathVariable String mes) {
        log.info("主题交换机->routingKey: {},mes: {}", routingKey, mes);
        messageService.sendMessageTopic(routingKey, mes);
        return "主题交换机,发送成功";
    }

    @GetMapping("/headers/{type}/{status}/{mes}")
    public String headers(@PathVariable String type, @PathVariable String status, @PathVariable String mes) {
        log.info("头部交换机—>type: {},mes: {}", type, status);
        messageService.sendMessageHeaders(type, status, mes);
        return "头部交换机,发送成功";
    }

    @GetMapping("/ttl/{mes}")
    public String ttl(@PathVariable String mes) {
        log.info("消息过期方式一->mes: {}", mes);
        messageService.sendMessageTtl(mes);
        return "消息过期方式一,发送成功";
    }

    @GetMapping("/ttlA/{mes}")
    public String ttlA(@PathVariable String mes) {
        log.info("消息过期方式二->mes: {}", mes);
        messageService.sendMessageTtlA(mes);
        return "消息过期方式二,发送成功";
    }

    @GetMapping("/dlx/{mes}")
    public String dlx(@PathVariable String mes) {
        log.info("死信队列->mes: {}", mes);
        messageService.sendMessageDlx(mes);
        return "死信队列,发送成功";
    }
    @GetMapping("/dlx2/{mes}")
    public String dlx2(@PathVariable String mes) {
        log.info("死信队列->mes: {}", mes);
        messageService.sendMessageDlx2(mes);
        return "死信队列,发送成功";
    }
    @GetMapping("/dlx3/{mes}")
    public String dlx3(@PathVariable String mes) {
        log.info("死信队列,设置队列长度为5->mes: {}", mes);
        messageService.sendMessageDlx3(mes);
        return "死信队列,发送成功";
    }
    @GetMapping("/dlx4/{mes}")
    public String dlx4(@PathVariable String mes) {
        log.info("死信队列,手动确认消息->mes: {}", mes);
        messageService.sendMessageDlx4(mes);
        return "死信队列,手动确认消息,发送成功";
    }
}
