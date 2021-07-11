package com.tata.amqp.exchange.headers;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeaderExchange {
    public static String EXCHANGE_NAME = "header-exchange";
    public static String QUEUE_NAME_1 = "header-queue1";
    public static String QUEUE_NAME_2 = "header-queue2";
    public static String QUEUE_NAME_3 = "header-queue3";

    //Routing Key
    public static String Routing_KEY = "";

    public void createExchangeAndQueue() {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.HEADER.getExchangeName(), true);

            //Queue -1
            //Headers
            Map<String, Object> map = null;
            map = new HashMap<>();
            map.put("x-match", "any");
            map.put("First", "A");
            map.put("Fourth", "D");
            channel.queueDeclare(QUEUE_NAME_1, true, false, false, null);
            //Queuebinding
            channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, Routing_KEY, map);

            //Queue2
            map = new HashMap<>();
            map.put("x-match", "any");
            map.put("Third", "C");
            map.put("Fourth", "D");
            channel.queueDeclare(QUEUE_NAME_2, true, false, false, null);
            //Queuebinding
            channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, Routing_KEY, map);

            //Queue3
            map = new HashMap<>();
            map.put("x-match", "any");
            map.put("First", "A");
            map.put("Third", "C");
            channel.queueDeclare(QUEUE_NAME_3, true, false, false, null);
            //Queuebinding
            channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME, Routing_KEY, map);

            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
