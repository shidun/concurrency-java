package com.mmall.concurrency.mqStart.consumer;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(RabbitMqConfig.port);
        connectionFactory.setVirtualHost(RabbitMqConfig.virtualHost);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";

//        channel.exchangeDeclare();
        String msg = "hello-consumer-message";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        }
//        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
