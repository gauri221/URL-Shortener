package com.example.URLshortener.analytics;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.URLshortener.url.UrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final UrlService urlService;

    @GetMapping("/me")
    public ResponseEntity<List<AnalyticsResponseDto>> userAnalytics(){
        return ResponseEntity.ok(urlService.getUserAnalytics());
    }
}
