package com.example.weather_info_for_pincode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class WeatherInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String pincode;
    private LocalDate forDate;
    private String weatherDescription;
    private double temperature;
    private double humidity;
}
