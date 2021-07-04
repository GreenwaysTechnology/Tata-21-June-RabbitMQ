package com.tata.amqp.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Producer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {

        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {

            Channel channel = connection.createChannel();
            //create queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(QUEUE_NAME + "Created ");

            //Publish Message
            String message = args[0];
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" Sent " + message + " ");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
