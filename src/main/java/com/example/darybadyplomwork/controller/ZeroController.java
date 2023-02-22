package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ZeroController {
    @GetMapping("/")
    public String zeroMapping(HttpServletRequest request) {
        System.out.println("homeMapping");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() == UserRole.USER)
            return "redirect:/search?size=10";
        System.out.println(user.getRole());
        if (user.getRole() == UserRole.ADMIN)
            return "redirect:/usertable?size=10";
        if (user.getRole() == UserRole.MANAGER) {
            return "redirect:/announcesmanagerlist";
        } else return "redirect:/search";
    }
}
