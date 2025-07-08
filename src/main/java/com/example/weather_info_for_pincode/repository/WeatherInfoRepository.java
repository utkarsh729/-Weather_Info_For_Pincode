package com.example.weather_info_for_pincode.repository;

import com.example.weather_info_for_pincode.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
    Optional<WeatherInfo> findByPincodeAndForDate(String pincode, LocalDate forDate);
}
