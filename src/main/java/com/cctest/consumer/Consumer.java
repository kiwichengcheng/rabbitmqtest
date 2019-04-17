package com.cctest.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

    public static void main(String[] args) throws Exception{
        //ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);

        //Connection
        Connection connection = connectionFactory.newConnection();

        //Channel
        Channel channel = connection.createChannel();

        String exchangeName = "test_exchange";
        String routingKey = "test.exchange";
        String queueName = "test_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        channel.queueDeclare(queueName, true, false, false, null);


        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,true, new MyConsumer(channel));
    }
}
