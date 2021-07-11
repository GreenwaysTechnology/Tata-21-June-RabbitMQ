package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TTLQueuesUsingArgs {
    private static final String EXCHANGE_NAME = "demo-exchange";
    private static final String QUEUE_NAME = "demo-queue";
    private static final String ROUTING_KEY = "demokey";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            String message = "Hello RabbitMQ";

            Map<String, Object> queueArgs = new HashMap<>();
            queueArgs.put("x-message-ttl", 10000);
            channel.queueDeclare(QUEUE_NAME, true, false, false, queueArgs);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
            System.out.println(QUEUE_NAME + " " + EXCHANGE_NAME + "Message has been published");
            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }
}
