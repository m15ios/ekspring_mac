package ru.m15.ekspring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.ParsingAndAnalyseService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.m15.ekspring.config.RabbitConfig.rabbitQueue;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerServiceImpl {


    //    private final StorageService storage;
    private final FeedLinkRepository repository;
    private final ParsingAndAnalyseService parserLinks;
    private final ParserDataServiceImpl parserData;
    private final org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory;

    // cron attribute in app.yaml
    @Scheduled(cron = "${full-check-interval-in-cron}")
    void checkFeedLinks() throws Exception {
        this.checkSaved();
        this.checkParsedLinks();

        org.springframework.amqp.rabbit.connection.Connection connection = null;
        com.rabbitmq.client.Channel channel = null;

        try {
            connection = connectionFactory.createConnection();
            channel = connection.createChannel(false);
            int rmqCnt = (int) channel.messageCount(rabbitQueue);
            log.info("\n\n--------- RabbitMQ has " + rmqCnt + " packages -------\n");
            if (rmqCnt == 0) {
                this.checkInline();
            }
        } finally {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        }

    }

    private void checkInline() {
        List<FeedLink> feedLinks = repository.findByState(FeedState.INLINE);
        feedLinks.forEach(feedItem -> {
            log.info("checkFeedInline " + feedItem.getUrlSource());
        });
    }

    private void checkSaved() {
        // found feedLinks with state.SAVED
        // push to IN_PARSE and execute ParserService
        List<FeedLink> feedLinks = repository.findByState(FeedState.SAVED);

        feedLinks.forEach(feedItem -> {
            log.info("checkFeedLinks " + feedItem.getUrlSource());
            parserLinks.parsing(feedItem);
        });
    }

    private void checkParsedLinks() {
        List<FeedLink> feedLinks = repository.findByState(FeedState.PARSED_LINKS);

        // good way
        //feedLinks.stream().filter(/**logic here**/).findFirst();

        // way - grabli
        final int[] exitKey = {0};
        feedLinks.forEach(feedItem -> {
            if (exitKey[0] == 1) {
                return;
            }
            //Pattern pattern1 = Pattern.compile ("item");
            Pattern pattern1 = Pattern.compile("peugeot-3008");
            Matcher matcher1 = pattern1.matcher(feedItem.getUrlSource());
            if (matcher1.find()) {
                log.info("check for get good data " + feedItem.getUrlSource());
                parserData.parsing(feedItem);
                exitKey[0] = 1;
            }
        });
        log.info("check end");
    }


}
