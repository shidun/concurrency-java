package com.mmall.concurrency.mqStart.returnListen;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConfig.host);
        connectionFactory.setPort(RabbitMqConfig.port);
        connectionFactory.setVirtualHost(RabbitMqConfig.virtualHost);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.error";

//        channel.exchangeDeclare();
        String msg = "hello-reutrn-message";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                log.info("------handle-return------");
                log.info("replyCode:{}", i);
                log.info("replyText:{}", s);
                log.info("exchange:{}", s1);
                log.info("routingKey:{}", s2);
                log.info("properties:{}", basicProperties);
                log.info("bytes:{}", new String(bytes));
            }
        });
//        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
//        channel.close();
//        connection.close();
    }
}
