package com.tata.amqp.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        //Same COnnection Information
        Connection connection = RabbitMQConnectionUtil.getConnection();
        //Channel
        try {
            Channel channel = connection.createChannel();
            //Get The Queue From where you want to Consume
            //IF the Queue does not exit it will create, other wise it will try to use existing queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("App is Waiting for Messages.. To exit press CTRL + C");
            //Lambda /Callback Function to read message:
            DeliverCallback callback = (String consumerTag, Delivery delivery) -> {
                //read message
                System.out.println("Message is Available");
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message : " + message);
            };
            //Consume : Syntax - 1 : declaring callback function separely and consume it
//            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
//            });

//            Thread.sleep(5000);
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
                //read message
                System.out.println("Message is Available");
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Got Message : " + message);
            }, consumerTag -> {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
