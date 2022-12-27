package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.service.UserService;
import com.example.darybadyplomwork.utils.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        ParseUtil.parseErrors(session, model);
        return "login_page.html";
    }

    @PostMapping("/login")
    public String login(HttpSession session, String email, String password) {
        try {
            User user = userService.login(email, password);
            session.setAttribute("user", user);
        } catch (IllegalArgumentException e) {
            List<String> errors = List.of("wrongData");
            session.setAttribute("errors", errors);
            return "redirect:/login";
        }
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return "redirect:/";
    }
}
