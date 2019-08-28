package com.mmall.concurrency.mqStart.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Procuer2 {

    public static void main(String[] args) throws Exception{
        //1 创建一个connectionFacory 并进行基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.107");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过connection创建一个channel
        Channel channel = connection.createChannel();

        //4 通过channel 发送数据
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";
        String msg = "hello2 rabbitMq Direct Exchange Message222.";
        // 1 exchange 2 routingkey
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
