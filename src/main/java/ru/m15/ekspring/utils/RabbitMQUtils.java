package ru.m15.ekspring.utils;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.QueueingConsumer;
import ru.m15.ekspring.config.RabbitConfig;

public class RabbitMQUtils {

    private static final String QUEUE_NAME = RabbitConfig.rabbitQueue;

    private static final String rabbitmqUser = System.getenv("RABBITMQ_DEFAULT_USER");
    private static final String rabbitmqPass = System.getenv("RABBITMQ_DEFAULT_PASS");
    private static final String rabbitmqHost = System.getenv("RABBITMQ_HOSTNAME");

    public static int getCurrentQueueSize() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqHost);
        factory.setUsername(rabbitmqUser);
        factory.setPassword(rabbitmqPass);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclarePassive(QUEUE_NAME);
        int messageCount = (int) channel.messageCount(QUEUE_NAME);

        channel.close();
        connection.close();

        return messageCount;
    }

}
