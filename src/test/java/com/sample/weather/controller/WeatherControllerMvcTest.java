package com.sample.weather.controller;

import com.sample.weather.service.WeatherService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ComponentScan(basePackages = {"com.sample"})
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {

        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=london&apiKey=abcd"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"city\":\"london\",\"country\":\"uk\",\"description\":\"Raining\"}"));
    }
}

