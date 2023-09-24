package com.sample.weather.controller;

import com.sample.weather.exception.ClientRequestException;
import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.InvalidClientAPIKeyException;
import com.sample.weather.exception.InternalServerException;
import com.sample.weather.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({
            ClientRequestException.class,
            FieldValidationException.class,
            NoSuchElementException.class,
            InvalidClientAPIKeyException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleRequestError(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler({
            InternalServerException.class,
            RuntimeException.class
    })
    public ResponseEntity<ApiErrorResponse> handleInternalServerError(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    private ApiErrorResponse buildError(int code, String message) {
        return ApiErrorResponse.builder()
                .errorCode(code)
                .errorMessage(message)
                .build();
    }
}
