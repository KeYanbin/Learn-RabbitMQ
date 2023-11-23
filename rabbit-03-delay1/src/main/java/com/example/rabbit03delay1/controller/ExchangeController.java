package com.example.rabbit03delay1.controller;

import com.example.rabbit03delay1.service.MessageService;
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

    @GetMapping("/delay/{mes}")
    public String delay(@PathVariable String mes) {
        messageService.sendDelayMsg(mes);
        return "延迟消息发送成功,内容->" + mes;
    }

}
