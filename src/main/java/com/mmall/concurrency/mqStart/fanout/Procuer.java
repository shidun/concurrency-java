package com.mmall.concurrency.mqStart.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *  user.# 匹配 user.topic.data  和 user.topic
 *  user.* 匹配  user.topic 不匹配 user.topic.data  （* 只匹配一个单词）
 */
@Slf4j
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
        String exchangeName = "test_fanout_exchange2";
        String routingKey1 = "asdasd";

        // 1 exchange 2 routingkey
        for (int i = 0; i < 10; i++) {
            String msg1 = "topic rabbitMq ." + i ;
            channel.basicPublish(exchangeName, routingKey1, null, msg1.getBytes());
        }

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
