package com.sample.weather.configaration;

import com.sample.weather.interceptor.WeatherRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebInterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    WeatherRequestInterceptor weatherRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(weatherRequestInterceptor);
    }
}
