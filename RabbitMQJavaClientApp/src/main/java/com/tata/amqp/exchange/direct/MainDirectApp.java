package com.tata.amqp.exchange.direct;

public class MainDirectApp {
    public static void main(String[] args) throws InterruptedException {
        //Create Exchange,Queues,Bindings will be ready
        DirectExchange directExchange = new DirectExchange();
        //Publish
        Producer producer = new Producer();
        producer.publish();

        //Consumer to read
        Consumer consumer = new Consumer();
        consumer.receive();

        Thread.sleep(1000);


    }
}
