package com.mmall.concurrency.mqStart.consumer;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
public class MyConsumer extends DefaultConsumer {

    private Channel channel;
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        log.info("------------consumer message ----------");
//        log.info("consumerTag:{}", consumerTag);
//        log.info("envelope:{}", envelope);
//        log.info("properties:{}", properties);
//        log.info("body:{}", new String(body));
//        super.handleDelivery(consumerTag, envelope, properties, body);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((Integer) properties.getHeaders().get("key") == 0) {
            log.info("basicNack:{}", properties.getHeaders().get("key"));
            // 第二个参数是否持批量签收 第三个参数是否重新回队列
            channel.basicNack(envelope.getDeliveryTag(), false, true);
        } else {
            log.info("basicAck:{}", properties.getHeaders().get("key"));
            //false 不支持批量签收  下面方法主动去应答rabiitmq 表示这条消息处理完成
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }
}
