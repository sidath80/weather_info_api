package com.sample.weather.model;

public class WeatherData {

    private String city;
    private String country;
    private String description;

    private WeatherData() {
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private String city;
        private String country;
        private String description;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public WeatherData build() {
            WeatherData weatherData = new WeatherData();
            weatherData.city = this.city;
            weatherData.country = this.country;
            weatherData.description = this.description;
            return weatherData;
        }
    }
}
