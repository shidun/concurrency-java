package com.mmall.concurrency.mqStart.DLX;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(RabbitMqConfig.port);
        connectionFactory.setVirtualHost(RabbitMqConfig.virtualHost);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";

//        channel.exchangeDeclare();
        String msg = "hello-dlx-message";
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("utf-8")
                .expiration("10000")
                .build();
        for (int i = 0; i < 1; i++) {
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }
//        channel.close();
//        connection.close();
    }
}
