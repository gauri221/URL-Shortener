package com.example.URLshortener.url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenRequestDto {
    
    @NotBlank(message = "URL cannot be empty")
    @Pattern(regexp = "^https?://.*", message = "Must be a valid URL")
    private String longUrl;
}
