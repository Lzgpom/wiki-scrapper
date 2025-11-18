package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WikiScrapperControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 409
    @ExceptionHandler(WikiPageNotFound.class)
    public void handleWikiPageNotFound() {
    }
}