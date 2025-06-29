package com.sample.weather.configaration;

import com.sample.weather.interceptor.WeatherRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    WeatherRequestInterceptor weatherRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(weatherRequestInterceptor);
    }
}
