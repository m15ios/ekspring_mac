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
import ru.m15.ekspring.services.ParsingAndAnalyseService;
import ru.m15.ekspring.services.StorageService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerServiceImpl {


//    private final StorageService storage;
    private final FeedLinkRepository repository;
    private final ParsingAndAnalyseService parserLinks;
    private final ParserDataServiceImpl parserData;

    // cron attribute in app.yaml
    @Scheduled(cron = "${full-check-interval-in-cron}")
    void checkFeedLinks() {
        this.checkSaved();
        this.checkParsedLinks();
    }

    private void checkSaved(){
        // found feedLinks with state.SAVED
        // push to IN_PARSE and execute ParserService
        List<FeedLink> feedLinks = repository.findByState( FeedState.SAVED );

        feedLinks.forEach( feedItem -> {
            log.info( "checkFeedLinks " + feedItem.getUrlSource() );
            parserLinks.parsing( feedItem );
        } );
    }

    private void checkParsedLinks(){
        List<FeedLink> feedLinks = repository.findByState( FeedState.PARSED_LINKS );

        // good way
        //feedLinks.stream().filter(/**logic here**/).findFirst();

        // way - grabli
        final int[] exitKey = {0};
        feedLinks.forEach( feedItem -> {
            if( exitKey[0] == 1 ){
                return;
            }
            //Pattern pattern1 = Pattern.compile ("item");
            Pattern pattern1 = Pattern.compile ("peugeot-3008");
            Matcher matcher1 = pattern1.matcher (feedItem.getUrlSource());
            if( matcher1.find() ) {
                log.info("check for get good data " + feedItem.getUrlSource());
                parserData.parsing( feedItem );
                exitKey[0] = 1;
            }
        } );
        log.info("check end");
    }


}
