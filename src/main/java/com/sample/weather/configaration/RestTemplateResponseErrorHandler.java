package com.sample.weather.configaration;

import com.sample.weather.exception.InternalServerException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().value() != 200;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        throw new InternalServerException(httpResponse.getBody().toString());
    }

}