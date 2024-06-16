package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.admin.AdminSignInDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/admin")
    public String login(@Valid @ModelAttribute("loginForm") AdminSignInDTO adminSignInDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp?role=admin";
        }

        Optional<AdminDTO> adminDTOOptional = signUpService.validateAdminSignIn(adminSignInDTO.getEmailAddress(), adminSignInDTO.getPassword());
        if (adminDTOOptional.isPresent()) {
            // Fetch SignUpDTO details from the database
            List<SignUpDTO> signUpDetails = signUpService.getAllSignUpDetails();
            // Add signUpDetails to the model attribute
            model.addAttribute("detailsList", signUpDetails);
            // Additional actions can be performed here upon successful login
            return "registration/AdminHome.jsp"; // Redirect to admin home page
        } else {
            model.addAttribute("errorMsg", "Invalid email or password");
            return "registration/SignIn.jsp?role=admin"; // Redirect back to admin login page with error message
        }
    }
}