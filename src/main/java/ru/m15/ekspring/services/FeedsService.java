package ru.m15.ekspring.services;

import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
import ru.m15.ekspring.dto.RequestFeed;
import ru.m15.ekspring.dto.ResponseCommon;

@Service
public interface FeedsService {

    // pattern inversion of control (IOC)

    ResponseCommon addFeed( RequestFeed feed );

}
