package com.example.weather_info_for_pincode.service;

import com.example.weather_info_for_pincode.client.OpenWeatherClient;
import com.example.weather_info_for_pincode.dto.WeatherResponseDTO;
import com.example.weather_info_for_pincode.entity.PincodeInfo;
import com.example.weather_info_for_pincode.entity.WeatherInfo;
import com.example.weather_info_for_pincode.repository.PincodeInfoRepository;
import com.example.weather_info_for_pincode.repository.WeatherInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceImplTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private PincodeInfoRepository pincodeRepo;

    @Mock
    private WeatherInfoRepository weatherRepo;

    @Mock
    private OpenWeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCachedWeather_WhenExistsInDatabase() {
        String pincode = "226101";
        LocalDate date = LocalDate.of(2025, 7, 7);

        WeatherInfo cachedWeather = new WeatherInfo(
                1L, pincode, date, "partly cloudy", 34.2, 58.0
        );

        when(weatherRepo.findByPincodeAndForDate(pincode, date))
                .thenReturn(Optional.of(cachedWeather));

        WeatherResponseDTO response = weatherService.getWeather(pincode, date);

        assertEquals("226101", response.pincode());
        assertEquals("partly cloudy", response.description());
        assertEquals(34.2, response.temperature());
        assertEquals(58.0, response.humidity());

        verify(weatherRepo, times(1)).findByPincodeAndForDate(pincode, date);
        verifyNoInteractions(pincodeRepo, weatherClient);
    }

    @Test
    void shouldCallExternalApi_WhenNoCachedDataExists() {
        String pincode = "226101";
        LocalDate date = LocalDate.of(2025, 7, 7);

        when(weatherRepo.findByPincodeAndForDate(pincode, date)).thenReturn(Optional.empty());
        when(pincodeRepo.findById(pincode)).thenReturn(Optional.empty());
        when(weatherClient.getLatLong(pincode)).thenReturn(new double[]{26.8467, 80.9462});

        PincodeInfo savedPin = new PincodeInfo(pincode, 26.8467, 80.9462);
        when(pincodeRepo.save(any())).thenReturn(savedPin);

        WeatherResponseDTO apiResponse = new WeatherResponseDTO(pincode, date, "rain showers", 31.5, 70.0);
        when(weatherClient.getWeather(26.8467, 80.9462, pincode, date)).thenReturn(apiResponse);

        WeatherResponseDTO response = weatherService.getWeather(pincode, date);

        assertEquals("rain showers", response.description());
        assertEquals(31.5, response.temperature());
        assertEquals(70.0, response.humidity());

        verify(weatherRepo).findByPincodeAndForDate(pincode, date);
        verify(weatherClient).getLatLong(pincode);
        verify(weatherClient).getWeather(26.8467, 80.9462, pincode, date);
        verify(weatherRepo).save(any());
    }
}