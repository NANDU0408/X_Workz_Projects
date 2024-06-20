package com.xworkz.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // Invalidate session
        return "registration/SignIn.jsp"; // Redirect to login page
    }
}
