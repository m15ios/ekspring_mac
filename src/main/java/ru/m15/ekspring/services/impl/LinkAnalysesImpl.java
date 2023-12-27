package ru.m15.ekspring.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.JsonLink;
import ru.m15.ekspring.entities.enums.FeedState;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.FeedsService;
import ru.m15.ekspring.services.StorageService;
import ru.m15.ekspring.utils.Hash;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkAnalysesImpl {

    private final FeedLinkRepository repository;
    private final FeedsService feedsService;

    public void add(FeedLink feedLink) {

        var jsonlinks = feedLink.getLinks();

        String coreUrl = feedLink.getUrlSource();

        try {
            URL url = new URL(coreUrl);

            if (!jsonlinks.isBlank()) {
                ObjectMapper mapper = new ObjectMapper();
                var type = new TypeReference<List<JsonLink>>() {
                };
                List<JsonLink> result = mapper.readValue(jsonlinks, type);

                log.info("\n\n\n\n\n----------------------------------");
                result.forEach(item -> {
                    //log.info( "link" + item.getLink() );
                    log.info("body" + item.getBody());
                    //log.info( "meta" + item.getMeta() );
                    //log.info( "type" + item.getType() );

                    try {
                        URL absoluteUrl = new URL(url, item.getLink());
                        checkAndPutUrl(absoluteUrl.toString());

                        feedLink.setState(FeedState.PARSED_LINKS);
                        feedLink.setCountAttempts(0);
                        feedLink.setLastDateAttempt(LocalDateTime.now());
                        repository.save(feedLink);

                    } catch (MalformedURLException e) {
                        log.error("url is bad!!!" + url + item.getLink(), e);
                    }
                });


            }

        } catch (Exception e) {
            log.error("printStackTrace! " + e);
            //e.printStackTrace();
        }

    }

    void checkAndPutUrl(String url) {
        String hash = Hash.hashURL(url);
        List<FeedLink> result = this.repository.findByUrlHash(hash);
        if (result.size() > 1) {
            log.error("duplicate - collision of hash urls!");
            return;
        }
        if (result.size() == 1) {
            return;
        }

        RequestFeed artificialRequestFeed = new RequestFeed();
        artificialRequestFeed.setFeedUrl(url);
        // now it is null !!!! artificialRequestFeed.setDurationTime( LocalDateTime.now().toString() );
        feedsService.addFeed( artificialRequestFeed );
        log.info(artificialRequestFeed.toString());
    }


}
