package ru.m15.ekspring.services.impl;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.*;
import ru.m15.ekspring.repositories.FeedLinkRepository;
import ru.m15.ekspring.repositories.RulesRepository;
import ru.m15.ekspring.services.ParsingAndAnalyseService;
import ru.m15.ekspring.services.StorageService;
import ru.m15.ekspring.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserDataServiceImpl {

    private final StorageService storageService;

    private final RulesRepository repositoryR;
    private JsonRules rules;


    public void parsing(FeedLink feedLink) throws Exception {

        String links = feedLink.getLinks();
        log.info( "\r\r\r-----------------\r" + links );
        JsonLinks jsonLinks = new JsonLinks( links );
        log.info( jsonLinks.toString() );

        UUID minioId = feedLink.getMinio();
        if( minioId != null ) {
            this.setRulesFrom( feedLink.getUrlSource() );
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

    private void setRulesFrom( String urlSource ) throws Exception {
        log.info( "\n\n\nsetRulesFrom " + urlSource );
        String key = Strings.getKeyByUrl( urlSource );
        if( key != "" ){
            log.info( "getting rules... " + key );
            Rules rules = repositoryR.findByUrl( key );
            log.info( rules.toString() );
            this.rules = new JsonRules( rules.getData() );
            log.info( this.rules.toString() );
            log.info( "end rules...\n\n" );
        }
    }

    public void parsing( String html ) {
        log.info( "feedlink is parsing..." );

        if( this.rules != null ){
            parsingAuto( html );
        }
    }

    private void parsingAuto( String html ){

        Document doc = Jsoup.parse( html );

        String goodsTitle = this.getNode( doc.select(this.rules.getTitle().getDocPath()) );
        log.info( "found " + goodsTitle );

        doc.select(this.rules.getProperties().getDocPath()).forEach( element -> {
            String title = this.getNode( element.select(this.rules.getProperties().getName()) );
            String value = this.getNode( element.select(this.rules.getProperties().getValue()) );
            log.info( "found " + title + " :: " + value );
        });

    }

    private void parsingCheckroom( String html ){

        Document doc = Jsoup.parse( html );

        doc.select("div.props tr").forEach( element -> {
            String title = this.getNode( element.select("td:nth-child(1)") );
            String value = this.getNode( element.select("td:nth-child(2)") );
            log.info( "found " + title + " :: " + value );
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

