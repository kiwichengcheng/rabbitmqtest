package com.cctest.limit;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

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

        String exchangeName = "test_limit_exchange";
        String routingKey = "test.limit";
        String myMessage = "Hello rabbitmq";

        int i = 0;
        while (++i < 5) {

            channel.basicPublish(exchangeName, routingKey, null, myMessage.getBytes());
        }
        channel.close();
        connection.close();


    }

}
