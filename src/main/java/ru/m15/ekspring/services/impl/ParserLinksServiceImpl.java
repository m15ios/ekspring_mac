package ru.m15.ekspring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.services.ParsingAndAnalyseService;
import ru.m15.ekspring.services.StorageService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserLinksServiceImpl implements ParsingAndAnalyseService {

    private final StorageService storageService;

    @Override
    public void parsing(FeedLink feedLink) {
        UUID minioId = feedLink.getMinio();
        if( minioId != null ) {
            String html = storageService.loadData(feedLink.getMinio());
            this.parsing(html);
        } else {
            log.error( "feedlink has nothing" );
        }
    }

    @Override
    public void parsing( String html ) {
        Document doc = Jsoup.parse( html );

        String title = doc.select( "h1" ).toString();
        log.info( "title " + title );
    }
}
