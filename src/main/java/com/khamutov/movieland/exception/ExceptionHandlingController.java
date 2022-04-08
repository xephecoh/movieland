package com.khamutov.movieland.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        log.error("Exception has been thrown",ex);
        return ex.getMessage();
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMovieNotFoundException(Exception ex) {
        log.error("Exception has been thrown",ex);
        return ex.getMessage();
    }
    @ExceptionHandler(CurrencyRateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCurrencyNotFoundException(Exception ex) {
        log.error("Exception has been thrown",ex);
        return ex.getMessage();
    }
}
