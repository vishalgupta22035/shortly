package com.shortly.exception;

public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException(String message) {
        super(message);
    }
}