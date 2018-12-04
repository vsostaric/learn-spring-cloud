package org.locus.learn.knockknockjokester.configuration;

import knock.model.service.JokeService;
import knock.model.service.impl.JokeServiceImpl;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class JokesterConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        Queue queueSend = new Queue("jokester.send.queue");
        Queue queueReceive = new Queue("jokester.receive.queue");
        rabbitAdmin.declareQueue(queueSend);
        rabbitAdmin.declareQueue(queueReceive);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate knockKnockTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setReplyTimeout(1000L);
        template.setReceiveTimeout(1000L);
        return template;
    }

    @Bean
    public JokeService jokeService() {
        return new JokeServiceImpl();
    }

}
