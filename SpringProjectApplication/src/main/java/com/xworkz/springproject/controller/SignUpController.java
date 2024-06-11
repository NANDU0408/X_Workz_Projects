package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("signUp")
    public String signUp(@Valid @ModelAttribute("dto") SignUpDTO signUpDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/SignUp.jsp";
        }

        if (signUpService.checkEmailExists(signUpDTO.getEmailAddress())) {
            model.addAttribute("failureMessage", "Email already exists. Please try a different one.");
            return "registration/SignUp.jsp";
        }

        if (signUpService.checkPhoneNumberExists(signUpDTO.getMobileNumber())) {
            model.addAttribute("failureMessage", "Phone number already exists. Please try a different one.");
            return "registration/SignUp.jsp";
        }

        Optional<SignUpDTO> savedDto = signUpService.save(signUpDTO);
        if (savedDto.isPresent()) {
            model.addAttribute("successMessage", "Sign-Up successful! Your password is: " + savedDto.get().getPassword());
            return signUpDTO.getEmailAddress();
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }
        return "registration/SignUp.jsp";
    }
}