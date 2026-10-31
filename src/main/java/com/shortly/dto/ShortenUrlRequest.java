package com.shortly.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Size;
import java.time.Instant;

public class ShortenUrlRequest {

    @NotBlank(message = "URL is required")
    @Pattern(
            regexp = "https?://.+",
            message = "URL must start with http:// or https://"
    )
    private String originalUrl;

    @Future(message = "Expiration date must be in the future")
    private Instant expiresAt;

    @Size(min = 3, max = 20, message = "Custom alias must be between 3 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "Custom alias can contain only letters, numbers, hyphens and underscores"
    )
    private String customAlias;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }
}