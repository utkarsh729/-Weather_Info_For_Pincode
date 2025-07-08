package com.example.weather_info_for_pincode.service;

import com.example.weather_info_for_pincode.dto.WeatherResponseDTO;

import java.time.LocalDate;

public interface WeatherService {
    WeatherResponseDTO getWeather(String pincode, LocalDate forDate);
}
