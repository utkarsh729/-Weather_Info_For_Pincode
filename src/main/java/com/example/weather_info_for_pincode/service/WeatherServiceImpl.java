package com.example.weather_info_for_pincode.service;

import com.example.weather_info_for_pincode.client.OpenWeatherClient;
import com.example.weather_info_for_pincode.dto.WeatherResponseDTO;
import com.example.weather_info_for_pincode.entity.PincodeInfo;
import com.example.weather_info_for_pincode.entity.WeatherInfo;
import com.example.weather_info_for_pincode.repository.PincodeInfoRepository;
import com.example.weather_info_for_pincode.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private  PincodeInfoRepository pincodeRepo;
    @Autowired
    private  WeatherInfoRepository weatherRepo;
    @Autowired
    private  OpenWeatherClient client;

    @Override
    public WeatherResponseDTO getWeather(String pincode, LocalDate date) {
        Optional<WeatherInfo> cached = weatherRepo.findByPincodeAndForDate(pincode, date);
        if (cached.isPresent()) {
            WeatherInfo info = cached.get();
            return new WeatherResponseDTO(info.getPincode(), info.getForDate(), info.getWeatherDescription(), info.getTemperature(), info.getHumidity());
        }

        PincodeInfo pin = pincodeRepo.findById(pincode).orElseGet(() -> {
            double[] latLong = client.getLatLong(pincode);
            PincodeInfo p = new PincodeInfo(pincode, latLong[0], latLong[1]);
            return pincodeRepo.save(p);
        });

        WeatherResponseDTO weather = client.getWeather(pin.getLatitude(), pin.getLongitude(), pincode, date);
        if (weather == null || weather.description() == null) {
            throw new com.example.weather_info_for_pincode.exception.NotFoundException(
                    "Weather data not found for pincode " + pincode + " on date " + date
            );
        }
        WeatherInfo newWeather = new WeatherInfo(null, pincode, date, weather.description(), weather.temperature(), weather.humidity());
        weatherRepo.save(newWeather);
        return weather;
    }
}
