package com.tata.amqp.basic;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnectionUtil {
    public static Connection getConnection() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //set connection parameters
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        //connection
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
           // System.out.println("Connection Established");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
