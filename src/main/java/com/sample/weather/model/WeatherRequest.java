package com.sample.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Data
public class WeatherRequest {
    @NotBlank(message = "country is required")
    private String country;
    @NotBlank(message = "city is required")
    private String city;
}
