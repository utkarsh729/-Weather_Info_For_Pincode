package com.example.weather_info_for_pincode.controller;

import com.example.weather_info_for_pincode.dto.WeatherRequestDTO;
import com.example.weather_info_for_pincode.dto.WeatherResponseDTO;
import com.example.weather_info_for_pincode.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @PostMapping
    public ResponseEntity<WeatherResponseDTO> getWeather(@RequestBody WeatherRequestDTO request) {
        WeatherResponseDTO result = service.getWeather(request.pincode(), request.forDate());
        return ResponseEntity.ok(result);
    }
}
