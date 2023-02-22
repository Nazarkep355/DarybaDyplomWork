package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import com.example.darybadyplomwork.entity.enums.UserRole;
import com.example.darybadyplomwork.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
public class SingleAnnounceController {
    @Autowired
    private AnnounceService aService;


    @GetMapping("/announce/{id}")
    public String announcePage(@PathVariable("id") long id, Model model, @SessionAttribute Optional<User> user){
        Announcement announce = aService.findById(id);
        if(announce.getAStatus()!= AnnounceStatus.ACCEPTED&&user.get().getRole()!= UserRole.MANAGER){
            return "redirect:/";
        }
        model.addAttribute("announce",announce);
        return "single_announce.html";
    }
}
