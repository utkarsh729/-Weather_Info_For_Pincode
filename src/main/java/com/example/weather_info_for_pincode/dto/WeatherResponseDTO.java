package com.example.weather_info_for_pincode.dto;
import java.time.LocalDate;

public record WeatherResponseDTO(String pincode, LocalDate date, String description, double temperature, double humidity) {}