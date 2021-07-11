package com.tata.amqp.exchange.topic;


public class MainTopic {
    public static void main(String[] args) {

        //Create queues and exchanges
        TopicExchange topicExchange = new TopicExchange();
        topicExchange.createExchangeAndQueue();

        //Publish message
        Publisher publisher = new Publisher();
        publisher.publish();
        //read  messages
        Consumer consumer = new Consumer();
        consumer.receive();

    }
}
