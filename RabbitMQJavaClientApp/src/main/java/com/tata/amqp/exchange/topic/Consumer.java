package com.tata.amqp.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tata.amqp.basic.RabbitMQConnectionUtil;
import com.tata.amqp.exchange.fanout.FanoutExchange;
import com.tata.amqp.exchange.headers.HeaderExchange;

import java.io.IOException;

public class Consumer {
    public void receive() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            DeliverCallback consumer1 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue 1 " + message);
            };
            channel.basicConsume(HeaderExchange.QUEUE_NAME_1,true,consumer1, consumerTag -> {});

            DeliverCallback consumer2 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue 2 " + message);
            };
            channel.basicConsume(HeaderExchange.QUEUE_NAME_2,true,consumer2,consumerTag -> {});

            DeliverCallback consumer3 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue 3" + message);
            };
            channel.basicConsume(HeaderExchange.QUEUE_NAME_3,true,consumer3,consumerTag -> {});

          // connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
