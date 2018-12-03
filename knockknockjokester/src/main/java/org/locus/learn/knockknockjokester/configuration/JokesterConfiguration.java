package org.locus.learn.knockknockjokester.configuration;

import knock.model.service.JokeService;
import knock.model.service.impl.JokeServiceImpl;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class JokesterConfiguration {

    private final String knockknockQueueName = "knockknock.joke.queue";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate knockKnockTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.knockknockQueueName);
        template.setQueue(this.knockknockQueueName);
        template.setReplyTimeout(10000L);
        return template;
    }

    @Bean
    public JokeService jokeService() {
        return new JokeServiceImpl();
    }

}
