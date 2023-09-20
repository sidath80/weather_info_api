package com.sample.weather.service;

import com.sample.weather.entity.Weather;
import com.sample.weather.exception.ServerException;
import com.sample.weather.repoitory.WeatherH2Repository;
import com.sample.weather.repoitory.WeatherRepository;
import com.weather.model.InlineResponse200;
import com.weather.model.InlineResponse200Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherH2Repository weatherH2Repository;

    public String getWeather(String country, String city) {
        InlineResponse200 weatherData = weatherRepository.getWeather(country, city);
        String description = weatherData.getWeather().stream()
                .map(InlineResponse200Weather::getDescription)
                .findAny()
                .orElseThrow(() -> new ServerException("weather details not found"));

        Weather weather = new Weather(description);
        weatherH2Repository.save(weather);
        return description;
    }
}
