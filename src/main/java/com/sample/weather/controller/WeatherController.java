package com.sample.weather.controller;

import com.sample.weather.model.WeatherData;
import com.sample.weather.service.WeatherService;
import com.sample.weather.validation.WeatherRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    WeatherRequestValidator weatherRequestValidator;

    @Autowired
    WeatherService weatherService;

    /**
     * Retrieve today's weather information.
     * This endpoint allows clients to retrieve information about the current weather.
     * @return A string describing today's weather.
     */
    @GetMapping("/v1/weather")
    public ResponseEntity<WeatherData> getWeatherByParam(@RequestParam(required = false) String country,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam(required = false) String apiKey
    ) {
        weatherRequestValidator.validate(country, city, apiKey);
        WeatherData weatherData = new WeatherData.Builder()
                .city(city)
                .country(country)
                .description(weatherService.getWeather(country, city))
                .build();

        return ResponseEntity.ok(weatherData);
    }
}