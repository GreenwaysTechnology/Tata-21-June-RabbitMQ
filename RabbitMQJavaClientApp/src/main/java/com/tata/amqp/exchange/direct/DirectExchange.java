package com.tata.amqp.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tata.amqp.basic.ExchangeType;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

public class DirectExchange {
    //EXCHNAGE Name
    public static String EXCHANGE_NAME = "direct-exchange";
    //Queues
    public static String QUEUE_NAME_1 = "direct-queue-1";
    public static String QUEUE_NAME_2 = "direct-queue-2";
    public static String QUEUE_NAME_3 = "direct-queue-3";

    //Routing Keys
    public static String ROUTING_KEY_1 = "direct-key-1";
    public static String ROUTING_KEY_2 = "direct-key-2";
    public static String ROUTING_KEY_3 = "direct-key-3";

    public void createExchangeQueue() {
        try {
            Connection connection = RabbitMQConnectionUtil.getConnection();
            Channel channel = connection.createChannel();
            //Create Exchange
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT.getExchangeName(), true);

            //Queue Creations
            //First Queue
            channel.queueDeclare(QUEUE_NAME_1,true,false,false,null);
            //Bind Queue and Exchange
            channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,ROUTING_KEY_1);

            //Second Qeueue
            channel.queueDeclare(QUEUE_NAME_2,true,false,false,null);
            channel.queueBind(QUEUE_NAME_2,EXCHANGE_NAME,ROUTING_KEY_2);

            //Third Qeueue
            channel.queueDeclare(QUEUE_NAME_3,true,false,false,null);
            channel.queueBind(QUEUE_NAME_3,EXCHANGE_NAME,ROUTING_KEY_3);

            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}
