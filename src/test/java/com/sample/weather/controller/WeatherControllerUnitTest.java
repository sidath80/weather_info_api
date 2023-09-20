package com.sample.weather.controller;

import com.sample.weather.model.WeatherData;
import com.sample.weather.service.WeatherService;
import com.sample.weather.validation.WeatherRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherControllerUnitTest {

    @InjectMocks
    WeatherController controller;

    @Mock
    WeatherService weatherService;

    @Mock
    WeatherRequestValidator weatherRequestValidator;

    @Test
    public void getHelloWorld() {
        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");

        ResponseEntity<WeatherData> responseEntity = controller.getWeatherByParam("uk", "London", "f42b4e776912cc232bc85c9849b07074");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Raining", responseEntity.getBody().getDescription());
    }

}