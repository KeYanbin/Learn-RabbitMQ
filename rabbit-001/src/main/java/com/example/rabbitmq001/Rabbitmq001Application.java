package com.example.rabbitmq001;

import com.example.rabbitmq001.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Rabbitmq001Application {

    public static void main(String[] args) {
        SpringApplication.run(Rabbitmq001Application.class, args);
    }

}
