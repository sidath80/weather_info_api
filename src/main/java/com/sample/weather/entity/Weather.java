package com.sample.weather.entity;

import lombok.Builder;
import lombok.Getter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String description;

    String city;

    String country;

    LocalDateTime creationDateTime;
}
