package com.example.weather_info_for_pincode.dto;

import java.time.LocalDate;

public record   WeatherRequestDTO(String pincode, LocalDate forDate) {}
