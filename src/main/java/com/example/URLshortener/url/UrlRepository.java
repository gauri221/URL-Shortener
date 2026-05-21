package com.example.URLshortener.url;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.URLshortener.auth.User;

public interface UrlRepository extends JpaRepository<ShortUrl, Long>{

    Optional<ShortUrl> findByLongUrl(String longUrl);
    Optional<ShortUrl> findByShortCode(String shortCode);
    List<ShortUrl> findByCreatedBy(User createBy);
    boolean existsByShortCode(String shortCode);
    void deleteByShortCode(String shortCode);
}