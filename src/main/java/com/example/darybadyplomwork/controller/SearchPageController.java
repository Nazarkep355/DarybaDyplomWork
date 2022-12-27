package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class SearchPageController {
    @Autowired
    private AnnounceService service;

    @GetMapping("/search")
    public String AnnounceList(Pageable pageable, Model model, Optional<String> city, HttpServletRequest request) {
        if (city.isPresent()) {
            Page<Announcement> page = service.findByCity(city.get(), pageable);
            model.addAttribute("announces", page.getContent());
        } else {
            Page<Announcement> page = service.findByPageable(pageable);
            model.addAttribute("announces", page.getContent());
        }
        return "search_page.html";
    }
}
