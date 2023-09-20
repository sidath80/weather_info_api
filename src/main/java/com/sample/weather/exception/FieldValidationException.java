package com.sample.weather.exception;

public class FieldValidationException extends RuntimeException {

    public FieldValidationException(String message) {
        super(message);
    }
}
