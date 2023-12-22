package ru.m15.ekspring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.m15.ekspring.config.RabbitConfig;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.StorageService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GrabberServiceImpl {

    private final StorageService storage;
    private final FeedLinkRepository repository;

    // add listener rabbit queue
    // todo to think about double reading from rabbit : ?raceCondition - setCountAttempts
    @RabbitListener(queues = RabbitConfig.rabbitQueue )
    void load( String feedUUid ){
        log.info( "GrabberServiceImpl started with " + feedUUid  );

        // get feedlink - line from DB
        FeedLink feedLink = repository.findById( UUID.fromString( feedUUid ) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );

        feedLink.setState( FeedState.INLINE );
        feedLink.setCountAttempts( feedLink.getCountAttempts() + 1 );
        feedLink.setLastDateAttempt( LocalDateTime.now() );
        feedLink.setDurationDate( LocalDateTime.now().plusMinutes( 3 ) );
        repository.save(feedLink);

        log.info( "GrabberServiceImpl feed url - " + feedLink.getUrlSource() );
        downloadByUrlAndSave( feedLink );
    }

    private void downloadByUrlAndSave( FeedLink feedLink ){
        String url = feedLink.getUrlSource();
        try {
            String data = Jsoup.connect(url).get().html();
            UUID uid = UUID.randomUUID();
            storage.saveData( uid, data );
            feedLink.setState( FeedState.SAVED );
            feedLink.setMinio( uid );
            repository.save(feedLink);
            // push to second rabbit
        } catch ( IOException e ){
            log.error( "IO URL FAIL ", e );
        }
    }

}
