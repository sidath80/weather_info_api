package com.sample.weather.repoitory;

import com.sample.weather.exception.ClientRequestException;
import com.sample.weather.exception.InternalServerException;
import com.weather.model.InlineResponse200;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherRepositoryTest {

    @InjectMocks
    WeatherUpstreamRepository weatherRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    InlineResponse200 inlineResponse200;

    @Test
    void getWeatherSuccessForUkLondon() {
        when(inlineResponse200.getWeather().get(0).getDescription()).thenReturn("cloudy");
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenReturn(inlineResponse200);

        InlineResponse200 weather = weatherRepository.getWeather(anyString(),anyString());

        assertEquals(weather.getWeather().get(0).getDescription(), "cloudy");
    }

    @Test
    void getWeatherThrowsServerErrorForAnyString() {
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenThrow(InternalServerException.class);

        assertThrows(InternalServerException.class,() -> weatherRepository.getWeather(anyString(), anyString()));
    }

    @Test
    void getWeatherThrowsClientErrorForAnyString() {
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenThrow(ClientRequestException.class);

        assertThrows(ClientRequestException.class,() -> weatherRepository.getWeather(anyString(), anyString()));
    }
}