package com.ainews.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AinewsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AinewsPortalApplication.class, args);
    }
}
