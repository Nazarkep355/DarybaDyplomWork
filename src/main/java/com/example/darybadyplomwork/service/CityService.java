package com.example.darybadyplomwork.service;

import com.example.darybadyplomwork.entity.City;
import com.example.darybadyplomwork.repos.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<String> findCitiesNameByLocale(String locale) {
        List<City> cities = cityRepository.findAll();
        if (locale.equals("uk")) {
            return cities.stream().map(a -> a.getUkName()).collect(Collectors.toList());
        } else
            return cities.stream().map((a -> a.getEnName())).collect(Collectors.toList());

    }

    public List<String> findWithSimilarName(String name, String locale) {
        List<City> cities =
                cityRepository.findCitiesByEnNameContainingIgnoreCaseOrUkNameContainingIgnoreCase(name, name);
        if (locale.contains("uk")) {
            return cities.stream()
                    .map(City::getUkName)
                    .collect(Collectors.toList());
        } else {
            return cities.stream()
                    .map(City::getEnName)
                    .collect(Collectors.toList());
        }
    }

    public City findCityByName(String name) {
        List<City> cities = cityRepository.findAll();
        return cities.stream().filter(a -> {
            return a.getEnName().equals(name)
                    || a.getUkName().equals(name);
        }).findAny().orElseThrow();
    }

}