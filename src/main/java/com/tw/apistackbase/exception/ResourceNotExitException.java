package com.tw.apistackbase.exception;

import java.util.NoSuchElementException;

/**
 * Created by jxzhong. on 21/08/2017.
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotExitException extends NoSuchElementException {
    public ResourceNotExitException(String message) {
        super(message);
    }
}
