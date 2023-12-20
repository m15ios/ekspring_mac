package ru.m15.ekspring.services.impl;

//import jakarta.validation.constraints.Null;
import org.intellij.lang.annotations.Language;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.config.RabbitConfig;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.FeedsService;
import ru.m15.ekspring.utils.Hash;

import java.time.LocalDateTime;
import java.util.UUID;

//import java.sql.Timestamp;

/**
 * business logics for feeds controller (autolink by Spring)
 */

@Service
public class FeedsServiceImpl implements FeedsService {

    private final FeedLinkRepository repository;
    private final RabbitTemplate rabbitTemplate;

    // autolink by Spring
    FeedsServiceImpl( FeedLinkRepository repository, RabbitTemplate rabbitTemplate ){
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public ResponseCommon addFeed( RequestFeed feed ) {

        // language=json
        var links =     "[\n" +
                        "  {\n" +
                        "    \"link\": \"/home\",\n" +
                        "    \"body\": \"test body\",\n" +
                        "    \"meta\": \"meta test\",\n" +
                        "    \"type\": \"meta types\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"link\": \"/contacts\",\n" +
                        "    \"body\": \"test contacts body\",\n" +
                        "    \"meta\": \"meta contacts test\",\n" +
                        "    \"type\": \"meta contacts types\"\n" +
                        "  }\n" +
                        "]";

        var feedData = new FeedLink()
                .setUrlSource( feed.getFeedUrl() )
                .setState( FeedState.CREATED )
                .setCreateDate( LocalDateTime.now() )
                .setDurationDate( LocalDateTime.now() )
                .setCountAttempts( 0 )
                .setLastDateAttempt( null )
                .setUrlHash( Hash.hashURL(feed.getFeedUrl()) )
                .setLinks( links );
        var feedDuration = feed.getDurationTime();
        if( feedDuration != null ) {
            // feedDuration will be such like "2018-05-05T11:50:55"
            var feedDurationTime = LocalDateTime.parse(feedDuration);
            feedData.setDurationDate(feedDurationTime);
        }
        var result = this.repository.save( feedData );
        this.sendMessage(result.getId());
        return new ResponseCommon(
            200,
            "feed saved to" + result.getId().toString()
            );
    }

    private void sendMessageToRabbit(UUID message){
        String routingKey = RabbitConfig.rabbitQueue;
        rabbitTemplate.convertAndSend(routingKey, message.toString());
    }


    public void sendMessage(UUID message) {
        this.sendMessageToRabbit(message);
    }

}
