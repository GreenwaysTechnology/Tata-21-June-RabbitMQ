package com.tata.amqp.exchange.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Publisher {

    private final static String MESSAGE_1 = "First Header Message";
    private final static String MESSAGE_2 = "Second Header Message";
    private final static String MESSAGE_3 = "Third Header Message";

    public void publish() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();

            //Meta Data -First Message
            AMQP.BasicProperties properties = null;
            Map<String, Object> map = null;
            map = new HashMap<>();
            //header values
            map.put("First", "A");
            map.put("Fourth", "D");
            //Basic properties carry many meta data to exchange.
            properties = new AMQP.BasicProperties();
            //injecting headers and its values into basicproperties
            properties = properties.builder()
                    .headers(map)
                    .build();
            //Publish message with Properties
            channel.basicPublish(HeaderExchange.EXCHANGE_NAME, HeaderExchange.Routing_KEY, properties, MESSAGE_1.getBytes());
            System.out.println("Message Sent " + MESSAGE_1);


            //Meta Data -First Message
            map = new HashMap<>();
            //header values
            map.put("Third", "C");
            //Basic properties carry many meta data to exchange.
            properties = new AMQP.BasicProperties();
            //injecting headers and its values into basicproperties
            properties = properties.builder()
                    .headers(map)
                    .build();
            //Publish message with Properties
            channel.basicPublish(HeaderExchange.EXCHANGE_NAME, HeaderExchange.Routing_KEY, properties, MESSAGE_2.getBytes());
            System.out.println("Message Sent " + MESSAGE_2);

            //Meta Data -First Message
            map = new HashMap<>();
            //header values
            map.put("First", "A");
            map.put("Third", "C");

            //Basic properties carry many meta data to exchange.
            properties = new AMQP.BasicProperties();
            //injecting headers and its values into basicproperties
            properties = properties.builder()
                    .headers(map)
                    .build();
            //Publish message with Properties
            channel.basicPublish(HeaderExchange.EXCHANGE_NAME, HeaderExchange.Routing_KEY, properties, MESSAGE_3.getBytes());
            System.out.println("Message Sent " + MESSAGE_3);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
