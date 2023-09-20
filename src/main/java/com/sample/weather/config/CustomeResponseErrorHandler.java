package com.sample.weather.config;

import com.sample.weather.exception.ServerException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomeResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().value() != 200;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        throw new ServerException(
                httpResponse.getBody().toString()
        );
    }

}