package com.sample.weather.exception;

public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }
}
