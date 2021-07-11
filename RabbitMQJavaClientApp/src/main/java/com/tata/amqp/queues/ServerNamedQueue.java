package com.tata.amqp.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;

public class ServerNamedQueue {
    private static final String EXCHANGE_NAME = "server-namedExQueue";
    private static final String QUERY_NAME = ""; // IT must be empty string
    private static final String ROUTING_KEYS = "mykey";
    private static final String MESSAGE_1 = "Hello!!!";

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT.getExchangeName(), true);

            //Ask the Server to create and give Queue Name: Server Generated Queue
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEYS);

            //publish message
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEYS, null, MESSAGE_1.getBytes());

            DeliverCallback consumer = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(message);
                try {
                    Thread.sleep(5000);
                    //for deleting queue once the data is consumed
                    connection.close();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            //consume message;
            channel.basicConsume(queueName, true, consumer, consumerTag -> {
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
