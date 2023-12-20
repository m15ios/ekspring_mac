package ru.m15.ekspring.services;

import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.FeedLink;

@Service
public interface ParsingAndAnalyseService {

    void parsing( FeedLink feedLink );
    void parsing( String html );


}
