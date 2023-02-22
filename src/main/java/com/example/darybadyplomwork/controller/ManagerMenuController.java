package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.UserRole;
import com.example.darybadyplomwork.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class ManagerMenuController {
    @Autowired
    private AnnounceService announceService;

    @GetMapping("/announcesmanagerlist")
    public String announceList(@SessionAttribute User user, Model model, Pageable pageable) {
        if (user == null || user.getRole() != UserRole.MANAGER) {
            return "redirect:/";
        }
        Page<Announcement> page = announceService.findCreatedAnnounces(pageable);
        model.addAttribute("announces",page.getContent());
        return "search_page.html";
    }
}
