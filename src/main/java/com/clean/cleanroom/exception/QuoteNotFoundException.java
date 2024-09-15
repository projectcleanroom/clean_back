package com.clean.cleanroom.exception;

public class QuoteNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
