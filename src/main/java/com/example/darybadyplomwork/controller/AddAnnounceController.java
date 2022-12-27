package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.dto.AnnounceDTO;
import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.Status;
import com.example.darybadyplomwork.service.AnnounceService;
import com.example.darybadyplomwork.service.CityService;
import com.example.darybadyplomwork.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Controller
public class AddAnnounceController {
    @Autowired
    private CityService cityService;
    @Autowired
    private AnnounceService announceService;



    @GetMapping("/add")
    public String addPage(HttpServletRequest request, Model model) {
        String locale = Arrays.stream(request.getCookies()).filter((a) -> {
            return a.getName().contains("LOCALE");
        }).findAny().orElse(new Cookie("LOCALE", "en")).getValue();
        List<String> cities = cityService.findCitiesNameByLocale(locale);
        model.addAttribute("cities", cities);
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/";
        }
        return "add_announce_page.html";
    }

    @PostMapping("/add")
    public String addAnnounce(AnnounceDTO dto,
                              @RequestParam("photos") MultipartFile[] images,
                              @SessionAttribute User user) {
        List<String> photos = DownloadUtil.savePhotos(images);
        announceService.createAnnounceWithDTO(dto,photos,user);

        return "redirect:/";
    }

}
