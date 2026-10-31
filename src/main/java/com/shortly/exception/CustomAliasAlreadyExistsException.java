package com.shortly.exception;

public class CustomAliasAlreadyExistsException extends RuntimeException {

    public CustomAliasAlreadyExistsException(String message) {
        super(message);
    }
}