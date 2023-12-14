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
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.StorageService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GrabberServiceImpl {

    private final StorageService storage;
    private final FeedLinkRepository repository;

    // add listener rabbit queue
    @RabbitListener(queues = RabbitConfig.rabbitQueue )
    void load( String feedUUid ){
        log.info( "GrabberServiceImpl started with " + feedUUid  );

        String url = repository.findById( UUID.fromString( feedUUid ) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) )
                .getUrlSource();

        log.info( "GrabberServiceImpl feed url - " + url );
        downloadByUrlAndSave( url );
    }

    private void downloadByUrlAndSave( String url ){
        try{
            String data = Jsoup.connect(url).get().html();
            UUID uid = UUID.randomUUID();
            storage.saveData( uid, data );
        } catch ( IOException e ){
            log.error( "IO URL FAIL ", e );
        }
    }

}
