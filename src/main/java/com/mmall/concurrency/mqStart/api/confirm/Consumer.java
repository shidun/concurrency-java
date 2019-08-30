package com.mmall.concurrency.mqStart.api.confirm;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Consumer {
//
//    @Autowired
//    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) throws Exception{
        //1 创建一个connectionFacory 并进行基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 获取connection
        Connection connection = connectionFactory.newConnection();

        //通过connection获取channel
        Channel channel = connection.createChannel();

        //执行消息投递模式,消息的确认模式
        String exchangeName = "test-confirm-exchange";
        String routingKey = "confirm.#";
        String queueName = "confirm-queue";
        //声明 exchange
        channel.exchangeDeclare(exchangeName, "topic", true);
        //声明队列
        channel.queueDeclare(queueName, true, false, false, null);
        //绑定设置
        channel.queueBind(queueName, exchangeName, routingKey);

        //创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String result = new String(delivery.getBody());
            log.info("-- message --{}", result);
        }
    }
}
