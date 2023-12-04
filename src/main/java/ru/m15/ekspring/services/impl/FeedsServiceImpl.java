package ru.m15.ekspring.services.impl;

//import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.FeedsService;

import java.time.LocalDateTime;

//import java.sql.Timestamp;

/**
 * business logics for feeds controller (autolink by Spring)
 */

@Service
public class FeedsServiceImpl implements FeedsService {

    private final FeedLinkRepository repository;

    // autolink by Spring
    FeedsServiceImpl( FeedLinkRepository repository ){
        this.repository = repository;
    }

    @Override
    public ResponseCommon addFeed( RequestFeed feed ) {
        var feedData = new FeedLink()
                .setUrlSource( feed.getFeedUrl() )
                .setState( FeedState.CREATED )
                .setCreateDate( LocalDateTime.now() )
                .setDurationDate( LocalDateTime.now() )
                .setCountAttempts( 0 )
                .setLastDateAttempt( null );
        var feedDuration = feed.getDurationTime();
        if( feedDuration != null ) {
            // feedDuration will be such like "2018-05-05T11:50:55"
            var feedDurationTime = LocalDateTime.parse(feedDuration);
            feedData.setDurationDate(feedDurationTime);
        }
        var result = this.repository.save( feedData );
        return new ResponseCommon(
            200,
            "feed saved to" + result.getId().toString()
            );
    }

}
