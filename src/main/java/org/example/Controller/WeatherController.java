package org.example.Controller;

import org.example.Model.WeatherRecord;
import org.example.repository.WeatherRepository;
import org.example.Service.WeatherAnalysisService;
import org.example.Service.WeatherApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherApiService apiService;
    private final WeatherRepository repository;
    private final WeatherAnalysisService analysisService;

    public WeatherController(WeatherApiService apiService,
                             WeatherRepository repository,
                             WeatherAnalysisService analysisService) {
        this.apiService = apiService;
        this.repository = repository;
        this.analysisService = analysisService;
    }

    // GET /api/weather/{city} - fetch current weather and store it
    @GetMapping("/{city}")
    public WeatherRecord fetchAndStoreWeather(@PathVariable String city) {
        WeatherRecord record = apiService.fetchWeatherForCity(city);
        repository.save(record);
        return record;
    }

    // GET /api/weather - retrieve all stored weather records
    @GetMapping
    public List<WeatherRecord> getAllRecords() {
        return repository.findAll();
    }

    // GET /api/weather/{city}/average - compute and return average temperature
    @GetMapping("/{city}/average")
    public Map<String, Object> getAverageTemperature(@PathVariable String city) {
        Double avg = analysisService.computeAverageTemperatureForCity(city);
        return Map.of(
                "city", city,
                "averageTemperature", avg
        );
    }
}