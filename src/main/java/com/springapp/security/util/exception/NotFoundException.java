package com.springapp.security.util.exception;

/**
 * Created by 123 on 21.01.2018.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
