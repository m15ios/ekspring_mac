package ru.m15.ekspring.api.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.m15.ekspring.dto.ResponseCommon;
import ru.m15.ekspring.services.FeedsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class FeedsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedsService service;; // replace with your class

    @Container
    private static final PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void setUp() {
        database.start();
        System.setProperty("spring.datasource.url", database.getJdbcUrl());
        System.setProperty("spring.datasource.username", database.getUsername());
        System.setProperty("spring.datasource.password", database.getPassword());

    }

    @Test
    void addFeed() throws Exception {
        when(service.addFeed(any())).thenReturn(new ResponseCommon(1,"2"));

        mockMvc.perform(post("/v1/feeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"yourFieldName\": \"yourValue\"}")) // replace with your fields
                .andExpect(status().isOk())
                .andExpect(response -> {
                    assertTrue(response.getResponse().getContentAsString().contains("{\"stateCode\":1,\"stateDescr\":\"2\"}")); // replace with expected contents
                });
    }
}