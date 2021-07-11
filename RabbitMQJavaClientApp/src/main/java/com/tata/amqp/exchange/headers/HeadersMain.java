package com.tata.amqp.exchange.headers;

import com.rabbitmq.client.AMQP;
import com.tata.amqp.exchange.headers.HeaderExchange;
import com.tata.amqp.exchange.headers.Publisher;
import com.tata.amqp.exchange.topic.Consumer;

public class HeadersMain {
    public static void main(String[] args) {
        HeaderExchange headerExchange = new HeaderExchange();
        headerExchange.createExchangeAndQueue();

        Publisher publisher = new Publisher();
        publisher.publish();

        com.tata.amqp.exchange.topic.Consumer consumer = new Consumer();
        consumer.receive();


    }
}
