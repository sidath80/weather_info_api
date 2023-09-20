package com.sample.weather.service;

import alwaysbemark.example.model.InlineResponse200;
import alwaysbemark.example.model.InlineResponse200Weather;
import com.sample.weather.exception.ServerException;
import com.sample.weather.repoitory.WeatherH2Repository;
import com.sample.weather.repoitory.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherH2Repository weatherH2Repository;

    public String getWeather(String country, String city) {
        InlineResponse200 weatherData = weatherRepository.getWeather(country, city);
        String description = weatherData.getWeather().stream()
                .map(InlineResponse200Weather::getDescription)
                .findAny()
                .orElseThrow(() -> new ServerException("weather details not found"));

        com.sample.weather.entity.Weather weather = new com.sample.weather.entity.Weather(description);
        weatherH2Repository.save(weather);
        return description;
    }
}
