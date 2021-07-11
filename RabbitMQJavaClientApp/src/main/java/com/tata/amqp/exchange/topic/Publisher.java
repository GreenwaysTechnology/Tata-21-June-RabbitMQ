package com.tata.amqp.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publisher {

    //Messages
    private final static String MESSAGE_1 = "FIRST topic message";
    private final static String MESSAGE_2 = "SECOND topic message";
    private final static String MESSAGE_3 = "THIRD topic message";
    public void publish() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();

            //publish message:
            channel.basicPublish(TopicExchange.EXCHANGE_NAME,TopicExchange.ROUTING_KEY_1,null,MESSAGE_1.getBytes());
            System.out.println("Message Sent " + MESSAGE_1 );

            channel.basicPublish(TopicExchange.EXCHANGE_NAME,TopicExchange.ROUTING_KEY_2,null,MESSAGE_2.getBytes());
            System.out.println("Message Sent " + MESSAGE_2 );

            channel.basicPublish(TopicExchange.EXCHANGE_NAME,TopicExchange.ROUTING_KEY_3,null,MESSAGE_3.getBytes());
            System.out.println("Message Sent " + MESSAGE_3 );

            channel.basicPublish(TopicExchange.EXCHANGE_NAME,TopicExchange.ROUTING_KEY_4,null,"India -Mumbai".getBytes());
            System.out.println("Message Sent " + "India-Mumbai" );

            channel.basicPublish(TopicExchange.EXCHANGE_NAME,TopicExchange.ROUTING_KEY_4,null,"Singapore".getBytes());
            System.out.println("Message Sent " + "Singapore" );

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
