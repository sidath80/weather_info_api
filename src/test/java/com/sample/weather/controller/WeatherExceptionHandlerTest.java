package com.sample.weather.controller;

import com.sample.weather.exception.ClientRequestException;
import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.InvalidClientAPIKeyException;
import com.sample.weather.exception.InternalServerException;
import com.sample.weather.model.ApiErrorResponse;
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
    GeneralExceptionHandler weatherExceptionHandler;

    @Test
    void handleClientError() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleRequestError(new ClientRequestException("Client error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Client error")));
    }

    @Test
    void handleServerError() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleInternalServerError(new InternalServerException("Server error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR)),
                () -> assertThat(response.getBody().getErrorCode(), is(500)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Server error")));
    }

    @Test
    void handleRequestValidation() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleRequestError(new FieldValidationException("Field validation error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Field validation error")));
    }

    @Test
    void handleNoSuchElement() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleRequestError(new NoSuchElementException("No Such element error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("No Such element error")));
    }

    @Test
    void handleInvalidClientAPIKeyException() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleRequestError(new InvalidClientAPIKeyException("A valid api-key header is required"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST)),
                () -> assertThat(response.getBody().getErrorCode(), is(400)),
                () -> assertThat(response.getBody().getErrorMessage(), is("A valid api-key header is required")));
    }

    @Test
    void handleRuntimeException() {
        ResponseEntity<ApiErrorResponse> response = weatherExceptionHandler.handleInternalServerError(new RuntimeException("Runtime error"));
        assertAll("response",
                () -> assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR)),
                () -> assertThat(response.getBody().getErrorCode(), is(500)),
                () -> assertThat(response.getBody().getErrorMessage(), is("Runtime error")));
    }
}