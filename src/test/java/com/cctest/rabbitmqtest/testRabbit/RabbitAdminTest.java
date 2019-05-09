package com.cctest.rabbitmqtest.testRabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitAdminTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitAdimin(){
        Exchange exchange = new TopicExchange("spring_topic_exchange", false, false);
        FanoutExchange fanoutexchange = new FanoutExchange("spring_fanout_exchange", false, false);
        Queue queue = new Queue("spring_topic_queue", false);
        Queue fanoutqueue = new Queue("spring_fanout_queue", false);

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareExchange(fanoutexchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareQueue(fanoutqueue);
        //rabbitAdmin.declareBinding(new Binding("spring_topic_queue", Binding.DestinationType.QUEUE, "spring_topic_exchange","spring.topic", new HashMap<>()));

        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("spring.topic").and(new HashMap<>()));

        rabbitAdmin.declareBinding(BindingBuilder.bind(fanoutqueue).to(fanoutexchange));

        rabbitAdmin.purgeQueue("spring_topic_queue",false);
        System.out.println(rabbitAdmin);
    }

    @Test
    public void testSendMessage() throws Exception{
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","信息描述");
        messageProperties.getHeaders().put("type","自定义消息类型...");

        Message message = new Message("hello rabbit mq".getBytes(), messageProperties);


        rabbitTemplate.convertAndSend("topic001", "spring.topic001", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().getHeaders().put("desc","额外的修改信息描述");
                message.getMessageProperties().getHeaders().put("attr","额外增加的信息");
                return message;
            }
        });

    }

    @Test
    public void testSendMessage2() throws Exception{
        /*MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","信息描述");
        messageProperties.getHeaders().put("type","自定义消息类型...");
        messageProperties.setContentType("text/plain");

        Message message = new Message("消息1234".getBytes(), messageProperties);

*/
        rabbitTemplate.convertAndSend("topic001", "spring.topic001", "消息12345");

    }
}
