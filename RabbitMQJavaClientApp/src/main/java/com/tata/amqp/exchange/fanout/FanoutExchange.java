package com.tata.amqp.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;

public class FanoutExchange {
    //EXCHNAGE Name
    public static String EXCHANGE_NAME = "fanout-exchange";
    //Queues
    public static String QUEUE_NAME_1 = "fanout-queue-1";
    public static String QUEUE_NAME_2 = "fanout-queue-2";
    public static String QUEUE_NAME_3 = "fanout-queue-3";

    public static String ROUTING_KEY = "";

    public void createExchangeAndQueue() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            //Create Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.FANOUT.getExchangeName(), true);

            //First Queue
            channel.queueDeclare(QUEUE_NAME_1, true, false, false, null);
            //Binding
            channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, ROUTING_KEY);
            //Second Queue
            channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
            //binding
            channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, ROUTING_KEY);

            //Third Queue
            channel.queueDeclare(QUEUE_NAME_3, true, false, false, null);
            // binding
            channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME, ROUTING_KEY);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
