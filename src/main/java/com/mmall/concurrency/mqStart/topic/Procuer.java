package com.mmall.concurrency.mqStart.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  user.# 匹配 user.topic.data  和 user.topic
 *  user.* 匹配  user.topic 不匹配 user.topic.data  （* 只匹配一个单词）
 */
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
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.topic";
        String routingKey2 = "user.topic.data";
        String routingKey3 = "user.save";
        String msg1 = "topic rabbitMq ." + routingKey1;
        String msg2 = "topic rabbitMq ." + routingKey2;
        String msg3 = "topic rabbitMq ." + routingKey3;
        // 1 exchange 2 routingkey
        channel.basicPublish(exchangeName, routingKey1, null, msg1.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg2.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg3.getBytes());

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
