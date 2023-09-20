package com.sample.weather.repoitory;

import alwaysbemark.example.model.InlineResponse200;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class WeatherRepository {

    @Value("${weather.endpoint}")
    private String weatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public InlineResponse200 getWeather(String country, String city) {
        String URI = String.format("%s%s,%s&appid=%s", weatherUrl, city, country, apiKey);
        return restTemplate.getForObject(
                URI, InlineResponse200.class);
    }
}
