package com.tata.amqp.basic;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionTest {
    public static void main(String[] args) {
       ConnectionFactory connectionFactory = new ConnectionFactory();
       //set connection parameters
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
      //  connectionFactory.setVirtualHost("product");
        connectionFactory.setVirtualHost("/");
        //connection
        try {
            Connection connection = connectionFactory.newConnection();
            System.out.println("Connection Established");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
