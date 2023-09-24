package com.sample.weather.controller;

import com.sample.weather.service.AccessRateMonitorService;
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

    @MockBean
    AccessRateMonitorService accessRateMonitorService;

    @Test
    public void getWeatherReturnSuccessResponse() throws Exception {

        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");
        when(accessRateMonitorService.allowRequest(anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=london")
                        .header("api-key", "abcd-001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"city\":\"london\",\"country\":\"uk\",\"description\":\"Raining\"}"));
    }

    @Test
    public void getWeatherWithEmptyApiKeyThrowError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=london"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"errorCode\":400,\"errorMessage\":\"api-key can not be blank\"}"));
    }

    @Test
    public void getWeatherWithInvalidApiKeyThrowError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=london")
                .header("api-key", "apikeyvalue"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"errorCode\":400,\"errorMessage\":\"A valid api-key header is required\"}"));
    }

    @Test
    public void getWeatherWithEmptyCountryThrowError() throws Exception {
        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=&city=london")
                        .header("api-key", "abcd-001"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"errorCode\":400,\"errorMessage\":\"country is required\"}"));
    }

    @Test
    public void getWeatherWithEmptyCityThrowError() throws Exception {
        when(weatherService.getWeather(anyString(), anyString())).thenReturn("Raining");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=")
                        .header("api-key", "abcd-001"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"errorCode\":400,\"errorMessage\":\"city is required\"}"));
    }

    @Test
    public void getWeatherTooManyRequestsThrowError() throws Exception {
        when(accessRateMonitorService.allowRequest(anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather?country=uk&city=London")
                        .header("api-key", "abcd-001"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}

