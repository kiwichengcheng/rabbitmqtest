package com.cctest.sendBack;

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
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            //
        }
        System.out.println(new String(bytes));
        if((Integer)basicProperties.getHeaders().get("num") != 0){
            channel.basicAck(envelope.getDeliveryTag(), false);
        }else {
            channel.basicNack(envelope.getDeliveryTag(),false, true);
        }
    }
}
