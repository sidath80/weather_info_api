package com.sample.weather.interceptor;

import com.sample.weather.exception.FieldValidationException;
import com.sample.weather.exception.InvalidClientAPIKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class WeatherRequestInterceptor implements HandlerInterceptor {

    @Value("${weather.client-api-keys}")
    private List<String> clientApiKeys;

    final String API_HEADER_NAME = "api-key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().equals("/v1/weather")) {
            validateApiKey(request);
            validateRequestParameters(request);
        }
        return true;
    }

    private void validateApiKey(HttpServletRequest request) {
        if (null == request.getHeader(API_HEADER_NAME)
                || request.getHeader(API_HEADER_NAME).isEmpty()) {
            throw new FieldValidationException("api-key can not be blank");
        }
        if (!clientApiKeys.contains(request.getHeader(API_HEADER_NAME))) {
            throw new InvalidClientAPIKeyException("A valid api-key header is required");
        }
    }

    private void validateRequestParameters(HttpServletRequest request) {
        if (null == request.getParameter("city")
                || request.getParameter("city").isEmpty()) {
            throw new FieldValidationException("city is required");
        }
        if (null == request.getParameter("country")
                || request.getParameter("country").isEmpty()) {
            throw new FieldValidationException("country is required");
        }
    }
}
