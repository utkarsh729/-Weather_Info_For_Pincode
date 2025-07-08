package com.example.weather_info_for_pincode.client;

import com.example.weather_info_for_pincode.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class OpenWeatherClient {

    private final WebClient webClient;

    @Value("${openweather.api.key}")
    private String apiKey;

    public OpenWeatherClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public double[] getLatLong(String pincode) {
        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/zip")
                        .queryParam("zip", pincode + ",IN")
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return new double[]{
                Double.parseDouble(response.get("lat").toString()),
                Double.parseDouble(response.get("lon").toString())
        };
    }

    public WeatherResponseDTO getWeather(double lat, double lon, String pincode, LocalDate date) {
        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weather = (List<Map<String, Object>>) response.get("weather");

        return new WeatherResponseDTO(
                pincode,
                date,
                weather.getFirst().get("description").toString(),
                Double.parseDouble(main.get("temp").toString()),
                Double.parseDouble(main.get("humidity").toString())
        );
    }
}
