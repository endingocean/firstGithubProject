package com.ainews.portal.controller;

import com.ainews.portal.entity.News;
import com.ainews.portal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> getTodayNews() {
        List<News> news = newsService.getTopNewsByDate(LocalDate.now(), 5);
        return buildResponse(news);
    }

    @GetMapping("/history/{date}")
    public ResponseEntity<Map<String, Object>> getHistoryNews(@PathVariable String date) {
        try {
            LocalDate targetDate = LocalDate.parse(date);
            List<News> news = newsService.getTopNewsByDate(targetDate, 5);
            return buildResponse(news);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 400);
            error.put("message", "Invalid date format. Use yyyy-MM-dd");
            error.put("data", List.of());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/all/{date}")
    public ResponseEntity<Map<String, Object>> getAllNewsByDate(@PathVariable String date) {
        try {
            LocalDate targetDate = LocalDate.parse(date);
            List<News> news = newsService.getNewsByDate(targetDate);
            return buildResponse(news);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 400);
            error.put("message", "Invalid date format. Use yyyy-MM-dd");
            error.put("data", List.of());
            return ResponseEntity.badRequest().body(error);
        }
    }

    private ResponseEntity<Map<String, Object>> buildResponse(List<News> news) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "success");
        response.put("data", news);
        return ResponseEntity.ok(response);
    }
}
