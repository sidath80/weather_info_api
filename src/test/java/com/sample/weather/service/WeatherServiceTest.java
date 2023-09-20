package com.sample.weather.service;

import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.ServerException;
import com.sample.weather.repoitory.WeatherH2Repository;
import com.sample.weather.repoitory.WeatherRepository;
import com.weather.model.InlineResponse200;
import com.weather.model.InlineResponse200Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherServiceTest {

    @InjectMocks
    WeatherService weatherService;

    @Mock
    WeatherH2Repository weatherH2Repository;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    InlineResponse200 weatherData;

    @Mock
    InlineResponse200Weather weather;

    @Test
    void getWeather_success_for_ukLondon() {

        when(weatherRepository.getWeather(anyString(), anyString())).thenReturn(weatherData);
        when(weatherData.getWeather()).thenReturn(List.of(weather));
        when(weather.getDescription()).thenReturn("cloudy");

        String description = weatherService.getWeather("UK", "London");

        assertEquals(description, "cloudy");
        verify(weatherH2Repository).save(any(com.sample.weather.entity.Weather.class));
    }

    @Test
    void getWeather_throws_serverError_for_anyString() {
        when(weatherRepository.getWeather(anyString(), anyString())).thenThrow(ServerException.class);

        assertThrows(ServerException.class,() -> weatherService.getWeather(anyString(), anyString()));
        verify(weatherH2Repository,never()).save(any(com.sample.weather.entity.Weather.class));
    }

    @Test
    void getWeather_throws_clientError_for_anyString() {
        when(weatherRepository.getWeather(anyString(), anyString())).thenThrow(ClientException.class);

        assertThrows(ClientException.class,() -> weatherService.getWeather(anyString(), anyString()));
        verify(weatherH2Repository,never()).save(any(com.sample.weather.entity.Weather.class));
    }
}