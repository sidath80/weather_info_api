package com.sample.weather.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessRateMonitorService {

    private final int rate;
    private final int timeWindowHours;

    private Map<String, Bucket> requestCache = new HashMap<>();

    public AccessRateMonitorService(
            @Value("${weather.api.allowed-rate:5}") int rate,
            @Value("${weather.api.allowed-rate-window-hours:1}") int timeFrameHours) {
        this.rate = rate;
        this.timeWindowHours = timeFrameHours;
    }

    public synchronized boolean allowRequest(String apiKey) {
        var bucket = requestCache.computeIfAbsent(apiKey,
                key -> Bucket.builder().addLimit(getLimit()).build());
        return bucket.tryConsume(1);
    }

    private Bandwidth getLimit() {
        return Bandwidth.classic(rate+1, Refill.greedy(rate, Duration.ofHours(timeWindowHours)));
    }
}
