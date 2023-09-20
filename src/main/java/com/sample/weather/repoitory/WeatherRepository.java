package com.sample.weather.repoitory;

import com.weather.model.InlineResponse200;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class WeatherRepository {

    @Value("${weather.endpoint}")
    private String weatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public InlineResponse200 getWeather(String country, String city) {
        String URI = String.format("%s%s,%s&appid=%s", weatherUrl, city, country, apiKey);
        return restTemplate.getForObject(
                URI, InlineResponse200.class);
    }
}
