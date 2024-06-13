package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.ResetPasswordDTO;
import com.xworkz.springproject.dto.SignInDTO;
import com.xworkz.springproject.dto.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

            signUpService.sendEmail(savedDto.get());
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }
        return "registration/SignUp.jsp";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") SignInDTO signInDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp";
        }

        Optional<SignUpDTO> signUpDTOOptional = signUpService.validateSignIn(signInDTO.getEmailAddress(), signInDTO.getPassword());
        if (signUpDTOOptional.isPresent()) {
            if (signUpDTOOptional.get().getCount() == 0) {
                return "registration/ResetPassword.jsp"; // Redirect to reset password page
            } else if (signUpDTOOptional.get().getCount() > 1) {
                return "registration/Home.jsp"; // Redirect to home page if count is more than 1
            }
            model.addAttribute("message", "Login successful");
            return "registration/ResetPassword.jsp";
        } else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "registration/SignIn.jsp";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("resetPasswordForm") ResetPasswordDTO resetPasswordDTO, Model model) {
        // Validate the new password
//        if (resetPasswordDTO.getNewPassword().length() != 16) {
//            model.addAttribute("errorMessage", "Password must be exactly 16 characters long.");
//            return "registration/ResetPassword.jsp"; // Assuming you have a view for the reset password form
//        }

        if(!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmNewPassword())){
            model.addAttribute("errorMessage", "New Password and Confirm Password not matched");
            return "registration/ResetPassword.jsp";
        }

        // Update the password in the database
        Optional<SignUpDTO> signUpDTOOptional = signUpService.findByEmailAddress(resetPasswordDTO.getEmailAddress());

        System.out.println(signUpDTOOptional.get());
        if (signUpDTOOptional.isPresent()) {
            System.out.println("controller update by password "+signUpDTOOptional.get());

            SignUpDTO signUpDTO = signUpDTOOptional.get();
            signUpDTO.setPassword(resetPasswordDTO.getNewPassword());
            signUpService.updatePassword(signUpDTO); // Use updatePassword method to save the updated password

            // Redirect to login page with a success message
            model.addAttribute("successMessage", "Password reset successful. Please log in with your new password.");
            return "registration/SignIn.jsp";
        } else {
            // If user not found, redirect to a failure page or handle appropriately
            model.addAttribute("errorMessage", "User not found. Please try again.");
            return "registration/ResetPassword.jsp";
        }
    }

}