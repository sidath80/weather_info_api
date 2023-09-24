package com.sample.weather.service;

import com.sample.weather.entity.Weather;
import com.sample.weather.exception.InternalServerException;
import com.sample.weather.repoitory.WeatherH2Repository;
import com.sample.weather.repoitory.WeatherUpstreamRepository;
import com.weather.model.InlineResponse200;
import com.weather.model.InlineResponse200Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUpstreamRepository weatherRepository;
    private final WeatherH2Repository weatherH2Repository;

    public String getWeather(String country, String city) {
        InlineResponse200 weatherData = weatherRepository.getWeather(country, city);
        String description = weatherData.getWeather().stream()
                .map(InlineResponse200Weather::getDescription)
                .findAny()
                .orElseThrow(() -> new InternalServerException("weather details not found"));

        Weather weather = Weather.builder()
                .city(city)
                .country(country)
                .description(description)
                .creationDateTime(LocalDateTime.now()).build();
        saveWeatherInformation(weather);
        return description;
    }

    private void saveWeatherInformation(Weather weather){
        weatherH2Repository.save(weather);
    }
}
