package com.mmall.concurrency.example.mq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqServer {

    @RabbitListener(queues = QueueConstants.TEST)
    private void receive(String message) {
        log.info("{}", message);
    }
}
