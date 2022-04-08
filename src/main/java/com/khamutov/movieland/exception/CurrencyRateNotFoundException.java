package com.khamutov.movieland.exception;

public class CurrencyRateNotFoundException extends RuntimeException {
    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
