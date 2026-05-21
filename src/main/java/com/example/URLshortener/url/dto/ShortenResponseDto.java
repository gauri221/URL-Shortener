package com.example.URLshortener.url.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenResponseDto {
    private String longUrl;
    private String shortCode;
    private LocalDateTime expiryDate;
    private String createdBy;
    private Instant createdAt;
}
