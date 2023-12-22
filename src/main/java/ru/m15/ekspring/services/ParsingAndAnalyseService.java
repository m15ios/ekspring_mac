package ru.m15.ekspring.services;

import org.springframework.stereotype.Service;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.JsonLink;

import java.util.Map;

@Service
public interface ParsingAndAnalyseService {

    void parsing( FeedLink feedLink );
    Map<String, JsonLink> parsing(String html );


}
