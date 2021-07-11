package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AutoDeleteQueueConsumer {
    private static final String QUEUE_NAME = "demo-queue";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            DeliverCallback callback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Message Received " + message);
                System.out.println("Consumer Tag" + consumerTag);
                //if we call cancel method,the node will delete queue , if it has been marked with auto
                //delete property true
               // channel.basicCancel(consumerTag);
            };
            channel.basicConsume(QUEUE_NAME,true,callback,consumerTag -> {});

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
