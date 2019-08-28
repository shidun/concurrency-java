package com.mmall.concurrency.mqStart.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Consumer2 {
//
//    @Autowired
//    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) throws Exception{
        //1 创建一个connectionFacory 并进行基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.0.107");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);//自动重连
        connectionFactory.setNetworkRecoveryInterval(3000);//自动重连时间间隔

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3 通过connection创建一个channel
        Channel channel = connection.createChannel();

        //4 声明（创建）一个队列
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct";
        //声明一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //声明一个队列
        channel.queueDeclare(queueName, true, false, false, null);
        //建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        //5 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6 设置channel 是否自动ACK
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
