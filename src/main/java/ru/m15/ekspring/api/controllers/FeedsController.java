package ru.m15.ekspring.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;

import java.util.List;


@RestController
@RequestMapping("/v1/feeds")
public interface FeedsController {

    @PostMapping
    ResponseCommon addFeed( @RequestBody RequestFeed feed );


}
