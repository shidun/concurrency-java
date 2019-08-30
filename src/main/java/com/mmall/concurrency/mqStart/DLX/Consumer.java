package com.mmall.concurrency.mqStart.DLX;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.mmall.concurrency.mqStart.consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(RabbitMqConfig.port);
        connectionFactory.setVirtualHost(RabbitMqConfig.virtualHost);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //这就是普通的交换机和队列及路由
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");

        //arguments 这个属性要设置到声明队列上
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);
        //进行死信队列的声明
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        //手工签收必须关闭autoACK 下面第二个参数为false
        channel.basicConsume(queueName, false, new MyConsumer(channel));


    }
}
