package com.tata.amqp.exchange.fanout;

public class MainFanout {
    public static void main(String[] args) throws InterruptedException {
        //Create Exchange,Queues,Bindings will be ready
        FanoutExchange fanoutExchange = new FanoutExchange();
        //Publish
        Producer producer = new Producer();
        producer.publish();

        Consumer consumer = new Consumer();
        consumer.receive();

        Thread.sleep(1000);


    }
}
