package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TTLQueueDeletion {
    private static final String EXCHANGE_NAME = "demo-exchange";
    private static final String QUEUE_NAME = "demo-queue";
    private static final String ROUTING_KEY = "demokey";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            //Args
            Map<String, Object> queueArgs = new HashMap<>();
            queueArgs.put("x-expires", 10000);
            channel.queueDeclare(QUEUE_NAME, true, false, true, queueArgs);
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);

            String message= "Hello RabbitMQ";
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,message.getBytes());

            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
