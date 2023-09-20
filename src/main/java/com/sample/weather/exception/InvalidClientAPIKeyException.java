package com.sample.weather.exception;

public class InvalidClientAPIKeyException extends RuntimeException {

    public InvalidClientAPIKeyException(String message){
        super(message);
    }
}
