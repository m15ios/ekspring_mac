package ru.m15.ekspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EkspringApplication {

    public static void main(String[] args) {
        //System.out.printf("we are here!");
        SpringApplication.run(EkspringApplication.class, args);
    }



}