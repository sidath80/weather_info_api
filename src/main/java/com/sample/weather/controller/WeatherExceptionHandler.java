package com.sample.weather.controller;

import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.ServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<String> handleClientError(ClientException exception) {
        return ResponseEntity.status(400).body("Client Error: " + exception.getMessage());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<String> handleServerError(ServerException exception) {
        return ResponseEntity.status(500).body("Server Error : " + exception.getMessage());
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<String> handleRequestValidation(FieldValidationException exception) {
        return ResponseEntity.status(400).body("Validation Error : " + exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleRequestValidation(NoSuchElementException exception) {
        return ResponseEntity.status(500).body("Weather result not found: " + exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRequestValidation(RuntimeException exception) {
        return ResponseEntity.status(500).body("Downstream server Error: " + exception.getMessage());
    }
}
