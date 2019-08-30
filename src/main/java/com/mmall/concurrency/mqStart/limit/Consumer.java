package com.mmall.concurrency.mqStart.limit;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.mmall.concurrency.mqStart.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(RabbitMqConfig.port);
        connectionFactory.setVirtualHost(RabbitMqConfig.virtualHost);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        //1 限流   设置1 每次处理1条
        channel.basicQos(0, 1, false);
        //autoACK设置为false 手动ACK
        channel.basicConsume(queueName, false, new MyConsumer(channel));

    }
}
