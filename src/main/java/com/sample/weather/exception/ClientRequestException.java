package com.sample.weather.exception;

public class ClientRequestException extends RuntimeException {

    public ClientRequestException(String message) {
        super(message);
    }
}
