package com.sample.weather.repoitory;

import alwaysbemark.example.model.InlineResponse200;
import com.sample.weather.exception.ClientException;
import com.sample.weather.exception.ServerException;
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
    WeatherRepository weatherRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    InlineResponse200 inlineResponse200;

    @Test
    void getWeather_success_for_ukLondon() {
        when(inlineResponse200.getWeather().get(0).getDescription()).thenReturn("cloudy");
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenReturn(inlineResponse200);

        InlineResponse200 weather = weatherRepository.getWeather(anyString(),anyString());

        assertEquals(weather.getWeather().get(0).getDescription(), "cloudy");
    }

    @Test
    void getWeather_throws_serverError_for_anyString() {
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenThrow(ServerException.class);

        assertThrows(ServerException.class,() -> weatherRepository.getWeather(anyString(), anyString()));
    }

    @Test
    void getWeather_throws_clientError_for_anyString() {
        when(restTemplate.getForObject(anyString(),eq(InlineResponse200.class))).thenThrow(ClientException.class);

        assertThrows(ClientException.class,() -> weatherRepository.getWeather(anyString(), anyString()));
    }
}