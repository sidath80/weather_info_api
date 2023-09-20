package com.sample.weather.repoitory;

import com.sample.weather.entity.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherH2Repository extends CrudRepository<Weather, Long> {

    List<Weather> findByDescription(String description);

    Weather findById(long id);
}
