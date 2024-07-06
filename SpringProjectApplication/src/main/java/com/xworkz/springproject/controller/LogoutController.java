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

    @PostMapping("/logoutAdmin")
    public String logoutAdmin(HttpServletRequest request) {
        request.getSession().invalidate(); // Invalidate session
        return "registration/SignIn.jsp?role=admin"; // Redirect to login page
    }

    @PostMapping("/logoutDeptAdmin")
    public String logoutDeptAdmin(HttpServletRequest request) {
        request.getSession().invalidate(); // Invalidate session
        return "registration/SignIn.jsp?role=deptadmin"; // Redirect to login page
    }
}
