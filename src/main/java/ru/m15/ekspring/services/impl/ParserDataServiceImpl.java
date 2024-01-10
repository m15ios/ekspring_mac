package ru.m15.ekspring.services.impl;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserDataServiceImpl {

    private final StorageService storageService;

    public void parsing(FeedLink feedLink) {
        UUID minioId = feedLink.getMinio();
        if( minioId != null ) {
            String html = storageService.loadData(feedLink.getMinio());
            this.parsing(html);
        } else {
            log.error( "feedlink has nothing" );
        }
    }

    private String getNode( Elements elements ){
        if( elements != null ){
            var eF = elements.first();
            if( eF != null ) {
                String html = eF.html();
                html = html.replaceAll( "<br>", "\n--" );
                // TODO: Bad way!!!!
                //html = "<body>" + html + "</body>";
                //Elements res = Jsoup.parse( html ).select("body");
                //return res.first().text().trim();
                // TODO: second way
                return html.replaceAll("\\<.*?\\>", "");
            }
        }
        return "";
    }

    public void parsing( String html ) {
        log.info( "feedlink is parsing..." );

        Document doc = Jsoup.parse( html );

        doc.select("div.props tr").forEach( element -> {
            String title = this.getNode( element.select("td:nth-child(1)") );
            String value = this.getNode( element.select("td:nth-child(2)") );
            log.info( "found " + title + "::" + value );
        });

        String descr = this.getNode( doc.select("div.descr_txt") );
        log.info( "found " + descr );

        String goodsTitleExt = this.getNode( doc.select("h1[itemprop] span") );
        log.info( "found " + goodsTitleExt );

        String goodsTitle = this.getNode( doc.select("h1[itemprop]") );
        goodsTitle = goodsTitle.replace( goodsTitleExt, "" ).trim();
        log.info( "found " + goodsTitle );

        String price = this.getNode( doc.select("div.price .value") );
        price = price.replaceAll("[^0-9,.]", "");
        // or if need only numbers
        // price = price.replaceAll("\\D", "");
        log.info( "found " + price );

    }
}

