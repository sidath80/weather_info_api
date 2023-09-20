package com.sample.weather.controller;

import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.InvalidClientAPIKeyException;
import com.sample.weather.exception.ServerException;
import com.sample.weather.model.ApiError;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class WeatherExceptionHandlerTest {

    @InjectMocks
    WeatherExceptionHandler weatherExceptionHandler;

    @Test
    void handleClientError() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleClientError(new ClientException("Client error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Client error")));
    }

    @Test
    void handleServerError() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleServerError(new ServerException("Server error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR)),
                () -> assertThat(response.getBody().getErrorCode(), is(500)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Server error")));
    }

    @Test
    void handleRequestValidation() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleRequestValidation(new FieldValidationException("Field validation error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Field validation error")));
    }

    @Test
    void handleNoSuchElement() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleNoSuchElement(new NoSuchElementException("No Such element error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("No Such element error")));
    }

    @Test
    void handleInvalidClientAPIKeyException() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleInvalidClientAPIKeyException(new InvalidClientAPIKeyException("A valid api-key header is required"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("A valid api-key header is required")));
    }

    @Test
    void handleRuntimeException() {
        ResponseEntity<ApiError> response = weatherExceptionHandler.handleRuntimeException(new RuntimeException("Runtime error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR)),
                () -> assertThat(response.getBody().getErrorCode(), is(500)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Runtime error")));
    }
}