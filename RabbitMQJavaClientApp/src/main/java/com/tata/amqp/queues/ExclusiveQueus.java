package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExclusiveQueus {
    private static final String EXCHANGE_NAME = "demo-exchange";
    private static final String QUEUE_NAME = "demo-queue";
    private static final String ROUTING_KEY = "demokey";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            //exclusive true
            channel.queueDeclare(QUEUE_NAME, true, true, true, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            String message = "Hello RabbitMQ";
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
            System.out.println(QUEUE_NAME + " " + EXCHANGE_NAME + "Message has been published");

            DeliverCallback callback = (consumerTag, delivery) -> {
                String result = new String(delivery.getBody(), "UTF-8");
                System.out.println("Message Received " + result);
                System.out.println("Consumer Tag" + consumerTag);
            };
            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
            });
            Thread.sleep(5000);
            channel.close();

        } catch (IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
