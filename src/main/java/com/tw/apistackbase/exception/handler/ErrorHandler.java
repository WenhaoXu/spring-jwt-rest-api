package com.tw.apistackbase.exception.handler;

import com.tw.apistackbase.exception.ResourceNotExitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ResourceNotExitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error processResourceNotExitError(ResourceNotExitException ex) {
        return new Error(ex.getMessage());
    }

}
