package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MaxLengthQueueArgPublisher {
    private static final String EXCHANGE_NAME = "demo-exchange";
    private static final String QUEUE_NAME = "demo-queue";
    private static final String ROUTING_KEY = "demokey";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            Map<String, Object> queueArgs = new HashMap<>();
            queueArgs.put("x-max-length", 5);
           channel.queueDeclare(QUEUE_NAME, true, false, false, queueArgs);
           // channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            for (int i = 1; i <= 10; i++) {
                String message =  i + "";
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
                System.out.println(QUEUE_NAME + EXCHANGE_NAME + message + "has been published");
            }
            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
