package ru.m15.ekspring.api.controllers.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;
import ru.m15.ekspring.services.FeedsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedsControllerImplTest {

    @Mock
    FeedsService service;

    @InjectMocks
    FeedsControllerImpl controller;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addFeed() {
        RequestFeed requestFeed = new RequestFeed();
        requestFeed.setFeedUrl("http://test.ru/test.xml");
        requestFeed.setDurationTime(null);
        when(service.addFeed( requestFeed ))
            .thenReturn( new ResponseCommon( 200,"test feed saved is OK"));
        assertDoesNotThrow(() -> controller.addFeed( requestFeed ));
        verify( service ).addFeed( requestFeed );
    }

}