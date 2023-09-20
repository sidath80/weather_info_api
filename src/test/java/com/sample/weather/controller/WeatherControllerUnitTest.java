package com.sample.weather.controller;

import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.ServerException;
import com.sample.weather.model.WeatherRequest;
import com.sample.weather.model.WeatherResponse;
import com.sample.weather.service.AccessRateMonitorService;
import com.sample.weather.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherControllerUnitTest {

    @InjectMocks
    WeatherController controller;

    WeatherRequest weatherRequest;

    @Mock
    AccessRateMonitorService accessRateMonitorService;

    @Mock
    WeatherService weatherService;

    @BeforeEach
    public void setUp() {

        weatherRequest = WeatherRequest.builder()
                .city("Melbourne")
                .country("Australia")
                .build();
    }

    @Test
    public void getWeather_return_success_response() {
        when(accessRateMonitorService.allowRequest(anyString())).thenReturn(true);
        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");

        ResponseEntity<WeatherResponse> responseEntity = controller.getWeatherByParam(weatherRequest,"abcd-001");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Raining", responseEntity.getBody().getDescription());
    }

    @Test
    void getWeather_throws_serverError_for_anyString() {
        when(accessRateMonitorService.allowRequest(anyString())).thenReturn(true);
        when(weatherService.getWeather(anyString(), anyString())).thenThrow(ServerException.class);

        assertThrows(ServerException.class,() -> controller.getWeatherByParam(weatherRequest,"abcd-001"));
    }

    @Test
    void getWeather_throws_clientError_for_anyString() {
        when(accessRateMonitorService.allowRequest(anyString())).thenReturn(true);
        when(weatherService.getWeather(anyString(), anyString())).thenThrow(ClientException.class);

        assertThrows(ClientException.class,() -> controller.getWeatherByParam(weatherRequest,"abcd-001"));
    }

}