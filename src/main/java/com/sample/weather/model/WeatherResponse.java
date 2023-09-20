package com.sample.weather.model;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    private String city;
    private String country;
    private String description;
}
