package com.tata.amqp.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String MESSAGE = "Happy News, Our Company got Ranked 1st  Position";

    public void publish() {
        try {
            Connection connection = RabbitMQConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            channel.basicPublish(FanoutExchange.EXCHANGE_NAME, FanoutExchange.ROUTING_KEY, null, MESSAGE.getBytes());
            System.out.println("Message Published " + MESSAGE);
            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
