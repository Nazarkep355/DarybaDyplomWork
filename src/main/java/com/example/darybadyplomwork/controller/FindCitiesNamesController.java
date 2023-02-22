package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class FindCitiesNamesController {
    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public List<String> findCities(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        String locale = Arrays.stream(cookies).filter(a -> a.getName()
                        .toLowerCase().contains("locale"))
                .map(a -> a.getValue()).findAny().orElse("en");
        System.out.println(locale);
        List<String> cities = cityService.findWithSimilarName(name, locale);
        System.out.println(cities);
        return cities;
    }

}
