package com.tata.amqp.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicExchange {

    //Exchange
    public static final String EXCHANGE_NAME = "topic-exchange";

    //Queue
    public static final String QUEUE_NAME_1 = "topic-queue-1";
    public static final String QUEUE_NAME_2 = "topic-queue-2";
    public static final String QUEUE_NAME_3 = "topic-queue-3";

    //Routing Patterns and  Keys
    public static String ROUTING_PATTERN_1 = "asia.china.*";
    public static String ROUTING_PATTERN_2 = "asia.china.#";
    public static String ROUTING_PATTERN_3 = "asia.*.*";

    //keys
    public static String ROUTING_KEY_1 = "asia.china.nanjing";
    public static String ROUTING_KEY_2 = "asia.china";
    public static String ROUTING_KEY_3 = "asia.china.beijing";
    public static String ROUTING_KEY_4 = "asia.india.mumbai";

    public void createExchangeAndQueue(){

        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();

            //Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.TOPIC.getExchangeName(),true);

            //First Queue
            channel.queueDeclare(QUEUE_NAME_1,true,false,false,null);
            channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,ROUTING_PATTERN_1);

            //Second Queue
            channel.queueDeclare(QUEUE_NAME_2,true,false,false,null);
            channel.queueBind(QUEUE_NAME_2,EXCHANGE_NAME,ROUTING_PATTERN_2);

            //Third Queue
            channel.queueDeclare(QUEUE_NAME_3,true,false,false,null);
            channel.queueBind(QUEUE_NAME_3,EXCHANGE_NAME,ROUTING_PATTERN_3);



            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }




}
