package com.sample.weather.validation;

import com.sample.weather.exception.FieldValidationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherRequestValidator {

    private Map<String, Integer> keys;

    @PostConstruct
    void loadKeys() {
        t.start();
        keys = new HashMap<>(Map.of(
                "abcd", 0,
                "efgh", 0,
                "ijkl", 0,
                "mnop", 0,
                "qrst", 0
        ));
    }

    public void validate(String country, String city, String apiKey) {
        if (null == country || country.isBlank()) {
            throw new FieldValidationException("country is blank");
        }
        if (null == city || city.isBlank()) {
            throw new FieldValidationException("city is blank");
        }
        validateApiKey(apiKey);
    }

    private void validateApiKey(String apiKey) {
        if (null == apiKey || apiKey.isBlank()) {
            throw new FieldValidationException("apiKey is blank");
        }
        if (!keys.containsKey(apiKey)) {
            throw new FieldValidationException("apiKey is not valid");
        }
        if (keys.get(apiKey) >= 5) {
            throw new FieldValidationException("apiKey was used more than 5 times in a hour");
        }
        keys.put(apiKey, (keys.get(apiKey) + 1));

    }

    public Map getKeys() {
        return this.keys;
    }

    Thread t = new Thread(() -> {
        while (true) {
        try {
            Thread.sleep(1000 * 60 * 60);
            keys.replaceAll((k, v) -> 0);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }}});
}
