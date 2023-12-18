package ru.m15.ekspring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.StorageService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerServiceImpl {


    private final StorageService storage;
    private final FeedLinkRepository repository;

    // cron attribute in app.yaml
    @Scheduled(cron = "${full-check-interval-in-cron}")
    void checkFeedLinks() {

        // found feedLinks with state.SAVED
        // push to IN_PARSE and execute ParserService
        List<FeedLink> feedLinks = repository.findByState( FeedState.SAVED );

        feedLinks.forEach( feedItem -> {
            log.info( "checkFeedLinks " + feedItem.toString() );
        } );


    }


}
