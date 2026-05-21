package com.example.URLshortener.url;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.URLshortener.analytics.AnalyticsResponseDto;
import com.example.URLshortener.auth.User;
import com.example.URLshortener.auth.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    public ShortUrl shortenUrl(String longUrl){

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<ShortUrl> existing = urlRepository.findByLongUrl(longUrl);
        if (existing.isPresent()){
            return existing.get();
        }

        String hex = DigestUtils.md5DigestAsHex(longUrl.getBytes());
        String shortCode = hex.substring(0,6);

        Integer k=1;
        while(urlRepository.existsByShortCode(shortCode)){
            shortCode = hex.substring(0,6);
            shortCode = shortCode + k;
            k++;
        }

        ShortUrl saved = new ShortUrl(null, user, Instant.now(), longUrl, shortCode, null, 0L);

        urlRepository.save(saved);
        return saved;
    }

    public ShortUrl redirectUrl(String shortCode){

        Optional<ShortUrl> existing = urlRepository.findByShortCode(shortCode);
        if (existing.isEmpty()){
            throw new EntityNotFoundException("No Link found for the short url: " + shortCode);
        }

        ShortUrl url = existing.get();
        url.setClickCount(url.getClickCount() + 1);        
        urlRepository.save(url);
        return url;

    }

    public List<AnalyticsResponseDto> getUserAnalytics(){

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<ShortUrl> list = urlRepository.findByCreatedBy(user);  

        return list.stream()
            .map(url -> AnalyticsResponseDto.builder()
                .shortCode(url.getShortCode())
                .longUrl(url.getLongUrl())
                .clickCount(url.getClickCount())
                .createdAt(url.getCreatedAt())
                .build())
            .collect(Collectors.toList());
    }

}
