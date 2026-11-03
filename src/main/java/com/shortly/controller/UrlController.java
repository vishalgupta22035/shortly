
package com.shortly.controller;

import com.shortly.dto.ShortenUrlRequest;
import com.shortly.dto.ShortenUrlResponse;
import com.shortly.service.UrlService;
import com.shortly.model.Url;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortenUrlResponse shortenUrl(
            @Valid @RequestBody ShortenUrlRequest request) {

        return urlService.shortenUrl(request);
    }

    @GetMapping("/{shortCode}")
    public Url getUrlDetails(@PathVariable String shortCode) {
        return urlService.getUrlDetails(shortCode);
    }

    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
    }

    @GetMapping
    public Page<Url> getAllUrls(Pageable pageable) {
        return urlService.getAllUrls(pageable);
    }
}
