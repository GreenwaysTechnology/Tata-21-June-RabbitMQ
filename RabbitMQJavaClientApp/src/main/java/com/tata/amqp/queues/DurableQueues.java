package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;

public class DurableQueues {
    private static final String QUEUE_NAME = "tatadurable-queue";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
