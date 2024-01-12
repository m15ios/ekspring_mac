package ru.m15.ekspring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 see it if you do not repeat methods
 it is highly likely as rabbit
 org.apache.activemq.artemis.jms.client.DefaultConnectionProperties
 org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
 org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
 org.springframework.amqp.rabbit.connection.CachingConnectionFactory
 */

@Configuration
public class RabbitConfig {

    // routing key
    public static final String rabbitQueue = "feed.urls.queue";

    @Bean
    public Queue myQueue() {
        return new Queue(RabbitConfig.rabbitQueue);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("tempRoutingKey");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("tempExchange", true, false);
    }
}
