package com.sample.weather.controller;

import com.sample.weather.model.WeatherRequest;
import com.sample.weather.model.WeatherResponse;
import com.sample.weather.service.AccessRateMonitorService;
import com.sample.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class WeatherController {

    private final WeatherService weatherService;
    private final AccessRateMonitorService accessRateMonitorService;

    @GetMapping("/v1/weather")
    public ResponseEntity<WeatherResponse> getWeatherByParam(
            @Valid WeatherRequest weatherRequest,
            @RequestHeader("api-key") String apiKey) {

        if (accessRateMonitorService.allowRequest(apiKey)) {
            return ResponseEntity.ok(WeatherResponse.builder()
                    .city(weatherRequest.getCity())
                    .country(weatherRequest.getCountry())
                    .description(weatherService.getWeather(weatherRequest.getCountry(), weatherRequest.getCity()))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }
}