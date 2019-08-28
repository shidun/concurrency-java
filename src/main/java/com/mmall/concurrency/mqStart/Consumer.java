package com.mmall.concurrency.mqStart;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j

public class Consumer {
//
//    @Autowired
//    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) throws Exception{
        //1 创建一个connectionFacory 并进行基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.107");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        log.info("1:{}", connectionFactory.toString());
        Connection connection = connectionFactory.newConnection();
        //3 通过connection创建一个channel
        Channel channel = connection.createChannel();

        //4 声明（创建）一个队列
        String queueName = "test02";
        channel.queueDeclare(queueName, true, false, false, null);

        //5 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6 设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            //7 获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());

            log.info("消费端收到消息：{}", msg);
//            Envelope envelope = delivery.getEnvelope();
        }

    }
}
