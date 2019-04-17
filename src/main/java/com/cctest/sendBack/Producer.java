package com.cctest.sendBack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

        String exchangeName = "test_ack_exchange";
        String routingKey = "test.ack";
        String myMessage = "Hello rabbitmq ";

        int i = 0;
        while (++i < 6) {
            Map<String, Object> headers = new HashMap<>();
            headers.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();

            channel.basicPublish(exchangeName, routingKey, properties, (myMessage+i).getBytes());
        }
        channel.close();
        connection.close();


    }

}
