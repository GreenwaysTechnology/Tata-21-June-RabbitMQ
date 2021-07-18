package com.tata.amqp.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;

public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
        Connection connection = RabbitMQConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            //delete messages
            channel.queuePurge(RPC_QUEUE_NAME);
            channel.basicQos(1);
            System.out.println("Server is Wating For Request");

            Object monitor = new Object();

            DeliverCallback callback = (ConsumerTag, delivery) -> {
                AMQP.BasicProperties props = new AMQP.BasicProperties()
                        .builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();
                String response = " ";
                try {
                    String message = new String(delivery.getBody(), "UTF-8");
                    int n = Integer.parseInt(message);
                    System.out.println(" Message  " + n );
                    response = response + fib(n);

                } catch (RuntimeException e) {

                } finally {
                    //republish the response to client
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), props, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }

            };

            channel.basicConsume(RPC_QUEUE_NAME, false, callback, (consumerTag, sig) -> {
            });

            //code for waiting indefintly
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
