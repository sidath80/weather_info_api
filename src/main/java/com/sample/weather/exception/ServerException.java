package com.sample.weather.exception;

public class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }
}
