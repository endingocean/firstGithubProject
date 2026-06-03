package com.ainews.portal.config;

import com.ainews.portal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NewsService newsService;

    @Override
    public void run(String... args) {
        newsService.initializeSampleData();
    }
}
