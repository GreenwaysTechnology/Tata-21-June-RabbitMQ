package com.tata.amqp.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public void receive() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            //Callback for reading from Queue -1
            DeliverCallback consumer1 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue - 1 : " + message);
            };
            channel.basicConsume(DirectExchange.QUEUE_NAME_1,true,consumer1,consumerTag -> {});

            //Callback for reading from Queue -2
            DeliverCallback consumer2 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue - 2 : " + message);
            };
            channel.basicConsume(DirectExchange.QUEUE_NAME_2,true,consumer2,consumerTag -> {});

            //Callback for reading from Queue -3
            DeliverCallback consumer3 = (String consumerTag, Delivery delivery) -> {
                //read message
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message From Queue - 3 : " + message);
            };
            channel.basicConsume(DirectExchange.QUEUE_NAME_3,true,consumer3,consumerTag -> {});


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
