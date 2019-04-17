package com.cctest.limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {

    private Channel channel;
    public MyConsumer(Channel channel){
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        System.out.println(new String(bytes));

        //发送ACK
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
