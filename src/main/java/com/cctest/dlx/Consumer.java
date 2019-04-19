package com.cctest.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

        //普通的交换机和队列
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","dlx.exchange");//"dlx.exchange"可任意起名
        //这个arguments声明到队列上
        channel.queueDeclare(queueName, true, false, false, arguments);//表示当这个队列出现死信后悔发送到arguments的exchange上
        channel.queueBind(queueName,exchangeName,routingKey);

        //要进行死信队列的声明
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        channel.basicConsume(queueName,true, new MyConsumer(channel));
    }
}
