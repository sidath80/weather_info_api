package com.sample.weather.entity;

import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
