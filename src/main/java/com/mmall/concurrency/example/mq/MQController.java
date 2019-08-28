package com.mmall.concurrency.example.mq;

import com.mmall.concurrency.example.mq.rabbitmq.RabbitMQCLient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mq")
public class MQController {

    @Resource
    private RabbitMQCLient rabbitMQCLient;

    @RequestMapping("/send")
    public String send(@RequestParam("message") String message) {
        rabbitMQCLient.send(message);
        return "success";
    }
}
