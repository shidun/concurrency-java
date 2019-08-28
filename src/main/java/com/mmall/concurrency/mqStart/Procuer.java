package com.mmall.concurrency.mqStart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Procuer {

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
        for (int i = 0; i < 5; i++) {
            String msg = "hello world";
            // 1 exchange 2 routingkey
            channel.basicPublish("", "test02", null, msg.getBytes());
        }

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
