package com.clean.cleanroom.exception;

public class InvalidQuoteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidQuoteException(String message) {
        super(message);
    }
}