package ru.m15.ekspring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // routing key
    public static final String rabbitQueue = "feed.urls.queue";

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.port}")
    private int rabbitPort;

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.username}")
    private String rabbitUser;

    @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.password}")
    private String rabbitPass;

    @Bean
    public Queue myQueue() {
        return new Queue(RabbitConfig.rabbitQueue);
    }


    @Bean
    public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory() {
        org.springframework.amqp.rabbit.connection.CachingConnectionFactory connectionFactory = new org.springframework.amqp.rabbit.connection.CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUser);
        connectionFactory.setPassword(rabbitPass);
//        connectionFactory.setAutomaticRecoveryEnabled(true);
//        connectionFactory.setNetworkRecoveryInterval(10000);
        return connectionFactory;
    }

    @Bean
    public org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate() {
        org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate = new org.springframework.amqp.rabbit.core.RabbitTemplate(connectionFactory());
        return rabbitTemplate;
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
