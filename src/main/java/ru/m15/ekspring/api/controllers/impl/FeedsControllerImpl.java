package ru.m15.ekspring.api.controllers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.m15.ekspring.api.controllers.FeedsController;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;
import ru.m15.ekspring.services.FeedsService;

@Slf4j
@RestController
public class FeedsControllerImpl implements FeedsController {

    private final FeedsService service;

    // autolink to service by spring
    FeedsControllerImpl( FeedsService service ) {
        this.service = service;
    }

    @Override
    public ResponseCommon addFeed(RequestFeed feed) {
        log.info("addFeedController is executing");
        return this.service.addFeed( feed );
        // mock answer
        // return new ResponseCommon( 200, "vse normalno" )я
    }

}
