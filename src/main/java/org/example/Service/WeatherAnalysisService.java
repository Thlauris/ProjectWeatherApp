package org.example.Service;

import org.example.Model.WeatherRecord;
import org.example.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherAnalysisService {

    private final WeatherRepository repository;

    public WeatherAnalysisService(WeatherRepository repository) {
        this.repository = repository;
    }

    public Double computeAverageTemperatureForCity(String city) {
        List<WeatherRecord> records = repository.findByCity(city);
        if (records.isEmpty()) {
            return null;
        }
        return records.stream()
                .mapToDouble(WeatherRecord::getTemperature)
                .average()
                .orElse(Double.NaN);
    }
}