package com.sample.weather.controller;

import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.InvalidClientAPIKeyException;
import com.sample.weather.exception.ServerException;
import com.sample.weather.model.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiError> handleClientError(ClientException exception) {
        return ResponseEntity.status(400).body(buildError(400, exception.getMessage()));
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<ApiError> handleRequestValidation(FieldValidationException exception) {
        return ResponseEntity.status(400).body(buildError(400, exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElement(NoSuchElementException exception) {
        return ResponseEntity.status(400).body(buildError(400, exception.getMessage()));
    }

    @ExceptionHandler(InvalidClientAPIKeyException.class)
    public ResponseEntity<ApiError> handleInvalidClientAPIKeyException(InvalidClientAPIKeyException exception) {
        return ResponseEntity.status(400).body(buildError(400, exception.getMessage()));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiError> handleServerError(ServerException exception) {
        return ResponseEntity.status(500).body(buildError(500, exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(500).body(buildError(500, exception.getMessage()));
    }

    private ApiError buildError(int code, String message) {
        return ApiError.builder()
                .errorCode(code)
                .errorMessage(message)
                .build();
    }
}
