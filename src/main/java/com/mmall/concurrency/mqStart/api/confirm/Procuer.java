package com.mmall.concurrency.mqStart.api.confirm;

import com.mmall.concurrency.mqStart.RabbitMqConfig;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Procuer {

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
        channel.confirmSelect();
        String exchangeName = "test-confirm-exchange";
        String routingKey = "confirm.key";

        //发送消息
        String message = "hello confirm rabbitmq";
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                log.info("----ACK-----");
            }

            @Override//返回不成功
            public void handleNack(long l, boolean b) throws IOException {
                log.info("----No-ack-----");
            }
        });
//
//        channel.close();
//        connection.close();
    }
}
