package com.example.URLshortener.url;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.URLshortener.url.dto.ShortenRequestDto;
import com.example.URLshortener.url.dto.ShortenResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {
    
    private final UrlService urlService;

    @PostMapping("/short")
    public ResponseEntity<ShortenResponseDto> shortenUrl (@Valid @RequestBody ShortenRequestDto request){

        ShortUrl saved = urlService.shortenUrl(request.getLongUrl());   
        ShortenResponseDto response = ShortenResponseDto.builder()
            .shortCode(saved.getShortCode())
            .longUrl(saved.getLongUrl())
            .createdBy(saved.getCreatedBy().getName())
            .createdAt(saved.getCreatedAt())
            .expiryDate(saved.getExpiryDate())
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUrl (@PathVariable String shortCode){

        ShortUrl saved = urlService.redirectUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(saved.getLongUrl()))
            .build();
    }


}
