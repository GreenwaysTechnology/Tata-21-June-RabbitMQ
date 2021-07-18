package com.tata.amqp.rpc;

import com.rabbitmq.client.*;
import com.tata.amqp.basic.RabbitMQConnectionUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCPublisher implements AutoCloseable {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCPublisher() {
        connection = RabbitMQConnectionUtil.getConnection();
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        try (RPCPublisher rpcPublisher = new RPCPublisher()) {
            for (int i = 0; i < 100; i++) {
                String i_str = Integer.toString(i);
                System.out.println("Requesting fib " + i_str + " ");
                Thread.sleep(1000);
                String response = rpcPublisher.call(i_str);
                System.out.println("GOT " + response + " ");

            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String call(String message) throws IOException, InterruptedException {
        //send request : corrleationId,replyQue
        final String corrleationId = UUID.randomUUID().toString();
        //create queueName
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties()
                .builder()
                .correlationId(corrleationId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
        //Read Response
        //Store the responses into BlockingQueue datastructure
        //Blocking- dont close the current thread until the message is delivered.
        final BlockingQueue response = new ArrayBlockingQueue(1);
        //Listener for message
        DeliverCallback callback = (String consmerTag, Delivery delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrleationId)) {
                //store response into blocking queue
                try {
                    response.offer(new String(delivery.getBody(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        String consmerTag = channel.basicConsume(replyQueueName, true, callback, consumerTag -> {
        });
        String result = response.take().toString();
        //cancel the consumer
        channel.basicCancel(consmerTag);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
