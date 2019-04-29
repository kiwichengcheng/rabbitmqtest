package com.cctest.rabbitmqtest.testRabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitAdminTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void testRabbitAdimin(){
        Exchange exchange = new TopicExchange("spring_topic_exchange");
        Queue queue = new Queue("spring_topic_queue");
        rabbitAdmin.declareExchange(exchange);
        System.out.println(rabbitAdmin);
    }
}
