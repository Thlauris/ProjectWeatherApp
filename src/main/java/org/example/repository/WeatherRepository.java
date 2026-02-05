package org.example.repository;

import org.example.Model.WeatherRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WeatherRepository {

    private final List<WeatherRecord> records = new ArrayList<>();

    public List<WeatherRecord> findAll() {
        return new ArrayList<>(records);
    }

    public List<WeatherRecord> findByCity(String city) {
        return records.stream()
                .filter(r -> r.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public void save(WeatherRecord record) {
        records.add(record);
    }
}