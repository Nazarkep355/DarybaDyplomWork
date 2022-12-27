package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.service.UserService;
import com.example.darybadyplomwork.utils.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String regPage(Model model, HttpSession session){
        ParseUtil.parseErrors(session,model);
        return "reg_page.html";
    }
    @PostMapping("/register")
    public String register (HttpSession session,String email){
        if(userService.isEmailExist(email)){
            List<String> errors = new ArrayList<>();
            errors.add("emailInUse");
            session.setAttribute("errors",errors);
            return "redirect:/register";
        }
        userService.registerUser(email);
        return "redirect:/login";
    }
}
