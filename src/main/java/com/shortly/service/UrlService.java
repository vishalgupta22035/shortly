
package com.shortly.service;

import com.shortly.dto.ShortenUrlRequest;
import com.shortly.dto.ShortenUrlResponse;
import com.shortly.model.Url;
import com.shortly.repository.UrlRepository;
import com.shortly.exception.UrlExpiredException;
import com.shortly.exception.CustomAliasAlreadyExistsException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.NoSuchElementException;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Value("${shortly.base-url}")
    private String baseUrl;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int CODE_LENGTH = 7;

    private final SecureRandom random = new SecureRandom();

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {

        String shortCode;

        if (request.getCustomAlias() != null &&
                !request.getCustomAlias().isBlank()) {

            shortCode = request.getCustomAlias();

            if (urlRepository.existsByShortCode(shortCode)) {
                throw new CustomAliasAlreadyExistsException(
                        "Custom alias is already taken"
                );
            }

        } else {
            shortCode = generateUniqueShortCode();
        }

        Url url = new Url();

        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(Instant.now());
        url.setExpiresAt(request.getExpiresAt());
        url.setClickCount(0);

        Url savedUrl = urlRepository.save(url);

        String shortUrl = baseUrl + "/" + savedUrl.getShortCode();

        return new ShortenUrlResponse(
                savedUrl.getOriginalUrl(),
                shortUrl,
                savedUrl.getShortCode(),
                savedUrl.getCreatedAt(),
                savedUrl.getExpiresAt()
        );
    }

    private String generateUniqueShortCode() {

        String shortCode;

        do {
            StringBuilder code = new StringBuilder();

            for (int i = 0; i < CODE_LENGTH; i++) {
                int index = random.nextInt(CHARACTERS.length());
                code.append(CHARACTERS.charAt(index));
            }

            shortCode = code.toString();

        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new NoSuchElementException("Short URL not found"));

        if (url.getExpiresAt() != null &&
                url.getExpiresAt().isBefore(Instant.now())) {

            throw new UrlExpiredException("Short URL has expired");
        }

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public Url getUrlDetails(String shortCode) {

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new NoSuchElementException("Short URL not found"));
    }

    @Transactional
    public void deleteUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new NoSuchElementException("Short URL not found"));

        urlRepository.delete(url);
    }

    @Transactional(readOnly = true)
    public Page<Url> getAllUrls(Pageable pageable) {
        return urlRepository.findAll(pageable);
    }
}
