package com.sample.weather.repoitory;

import com.sample.weather.entity.Weather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan(basePackages = {"com.sample"})
@SpringBootTest
public class H2RepositoryTest {

    @Autowired
    private WeatherH2Repository weatherH2Repository;

    @Test
    @Transactional
    public void shouldSaveWeatherData() {

        Weather weather = Weather.builder()
                .city("Colombo")
                .country("Ceylon")
                .description("Sunny")
                .creationDateTime(LocalDateTime.now()).build();
        weather = weatherH2Repository.save(weather);

        Optional<Weather> resultOp = weatherH2Repository.findById(weather.getId());
        assertEquals(weather.getId(), resultOp.get().getId());
    }
}
