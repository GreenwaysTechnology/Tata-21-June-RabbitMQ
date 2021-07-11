package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;

public class DurableExchange {
    private static final String EXCHANGE_NAME = "server-namedExQueue";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT.getExchangeName(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
