package com.sample.weather.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com.sample"})
@SpringBootTest
class AccessRateMonitorServiceTest {

    @Autowired
    AccessRateMonitorService accessRateMonitorService;

    @Test
    void allowRequest() {
        IntStream.range(0, 10).forEach(i -> {
                    boolean allow = accessRateMonitorService.allowRequest("apiKey");
                    if (i > 5) {
                        assertFalse(allow);
                    } else {
                        assertTrue(allow);
                    }
                }
        );
    }
}