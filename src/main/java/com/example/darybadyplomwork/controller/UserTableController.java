package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.UserRole;
import com.example.darybadyplomwork.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class UserTableController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/usertable")
    public String table(Model model, @SessionAttribute User user, Pageable pageable) {
        if (user == null) {
            return "redirect:/";
        }
        if (user.getRole() == UserRole.ADMIN) {
            model.addAttribute("page", adminService.getUserPage(pageable));
            return "user_table.html";
        } else

            return "redirect:/";
    }

    @PostMapping("/updateUser")
    public String updatePoint(@SessionAttribute User user, String action, long id) {
        System.out.println(action);
        if (user == null || user.getRole() != UserRole.ADMIN)
            return "redirect:/home";
        adminService.updateUser(action, id);
        return "redirect:/usertable?size=10";

    }
}