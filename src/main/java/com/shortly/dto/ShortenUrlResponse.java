package com.shortly.dto;

import java.time.Instant;

public class ShortenUrlResponse {

    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private Instant createdAt;
    private Instant expiresAt;

    public ShortenUrlResponse(
            String originalUrl,
            String shortUrl,
            String shortCode,
            Instant createdAt,
            Instant expiresAt) {

        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}