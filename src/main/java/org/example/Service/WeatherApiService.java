package org.example.Service;

import org.example.Model.WeatherRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class WeatherApiService {

    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherRecord fetchWeatherForCity(String city) {

        String url = "https://wttr.in/" + city + "?format=j1";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("current_condition")) {
            throw new RuntimeException("Impossible de récupérer la météo pour " + city);
        }

        List<Map<String, Object>> conditions =
                (List<Map<String, Object>>) response.get("current_condition");

        Map<String, Object> current = conditions.get(0);

        // WTTR renvoie parfois une liste, parfois une string → on gère les deux
        double tempC = extractValue(current.get("temp_C"));
        double humidity = extractValue(current.get("humidity"));

        return new WeatherRecord(city, tempC, humidity, LocalDateTime.now());
    }

    private double extractValue(Object obj) {
        if (obj instanceof List<?> list) {
            return Double.parseDouble(list.get(0).toString());
        }
        return Double.parseDouble(obj.toString());
    }
}