package com.example.darybadyplomwork.utils;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ParseUtil {
    public static void parseErrors(HttpSession session, Model model) {
        List<String> errors = (List<String>) session.getAttribute("errors");
        if (errors!=null) {
            for (String er : errors) {
                model.addAttribute(er, true);
            }
            session.setAttribute("errors",null);
        }
    }
}
