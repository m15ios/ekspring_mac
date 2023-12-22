package ru.m15.ekspring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.JsonLink;
import ru.m15.ekspring.entities.JsonLinks;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.services.ParsingAndAnalyseService;
import ru.m15.ekspring.services.StorageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserLinksServiceImpl implements ParsingAndAnalyseService {

    private final StorageService storageService;
    private final FeedLinkRepository repository;

    @Override
    public void parsing(FeedLink feedLink) {
        UUID minioId = feedLink.getMinio();
        if( minioId != null ) {
            String html = storageService.loadData(feedLink.getMinio());
            Map<String, JsonLink> links = this.parsing(html);
            if( !links.isEmpty() ){

                JsonLinks jsonLinks = new JsonLinks( links );
                String jsonString = jsonLinks.createJson();
                log.info( "jsonString " + jsonString );

                if( !jsonString.isBlank() ){
                    feedLink.setLinks( jsonString );
                    repository.save( feedLink );
                    log.info( "\n\n\n\n ------------------- feedLink saved -------------- \n\n\n\n" );
                }

            }
        } else {
            log.error( "feedlink has nothing" );
        }
    }

    @Override
    public Map<String, JsonLink> parsing( String html ) {

        Map<String, JsonLink> links = new HashMap<>();

        Document doc = Jsoup.parse( html );
        doc.select("a[href]").forEach( element -> {
            String title = element.text().trim();
            String href = element.attr("href").trim();
            if( href.startsWith("/catalog/") ) {
                // links by key is exist OR contains empty string
                if( !links.containsKey(href) ){
                    links.put(href, new JsonLink(href,title,null,null) );
                } else {
                    var item = links.get(href);
                    if( item.getBody().isBlank() ){
                        item.setBody(title);
                        links.put(href, item);
                    }
                }
            }
        });
        log.info("\n\n\n\n\n-------------------- JsonLinks -----------------------------" );
        links.forEach( (key, value) -> {
            log.info("for " + key + " = " + value.getBody() );
        });

        return links;
    }
}

