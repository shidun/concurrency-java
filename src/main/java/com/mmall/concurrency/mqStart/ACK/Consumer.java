package com.mmall.concurrency.mqStart.ACK;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.mmall.concurrency.mqStart.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        //手工签收必须关闭autoACK 下面第二个参数为false
        channel.basicConsume(queueName, false, new MyConsumer(channel));


    }
}
