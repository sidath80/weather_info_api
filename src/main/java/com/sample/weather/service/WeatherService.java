package com.sample.weather.service;

import com.sample.weather.entity.Weather;
import com.sample.weather.exception.InternalServerException;
import com.sample.weather.repoitory.WeatherH2Repository;
import com.sample.weather.repoitory.WeatherUpstreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.weather.model.Data25WeatherGet200Response;
import com.weather.model.Data25WeatherGet200ResponseWeatherInner;


@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUpstreamRepository weatherRepository;
    private final WeatherH2Repository weatherH2Repository;

    public String getWeather(String country, String city) {
        Data25WeatherGet200Response weatherData = weatherRepository.getWeather(country, city);
        String description = weatherData.getWeather().stream()
                .map(Data25WeatherGet200ResponseWeatherInner::getDescription)
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
