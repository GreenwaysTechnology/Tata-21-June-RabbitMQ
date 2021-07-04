package com.tata.amqp.exchange.direct;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private final static String MESSAGE_1 = "First Direct Message";
    private final static String MESSAGE_2 = "Second Direct Message";
    private final static String MESSAGE_3 = "Third Direct Message";

    public void publish() {
        try {
            Connection connection = RabbitMQConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //Publish message into Exchange with right Routing Key.
            channel.basicPublish(DirectExchange.EXCHANGE_NAME, DirectExchange.ROUTING_KEY_1, null, MESSAGE_1.getBytes());
            System.out.println("Message Published " + MESSAGE_1);

            channel.basicPublish(DirectExchange.EXCHANGE_NAME, DirectExchange.ROUTING_KEY_2, null, MESSAGE_2.getBytes());
            System.out.println("Message Published " + MESSAGE_2);

            channel.basicPublish(DirectExchange.EXCHANGE_NAME, DirectExchange.ROUTING_KEY_3, null, MESSAGE_3.getBytes());
            System.out.println("Message Published " + MESSAGE_3);

            channel.basicPublish(DirectExchange.EXCHANGE_NAME, DirectExchange.ROUTING_KEY_3, null, "Message New".getBytes());
            System.out.println("Message Published " + "Message New");

            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
