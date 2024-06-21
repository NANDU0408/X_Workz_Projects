package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.*;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData"})
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
            model.addAttribute("successMessage", "Sign-Up successful! Your password is sent to your email address: " + savedDto.get().getEmailAddress());
            signUpService.sendEmail(savedDto.get());
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }
        return "registration/SignUp.jsp";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") SignInDTO signInDTO, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp";
        }

        Optional<SignUpDTO> signUpDTOOptional = signUpService.findByEmailAddress(signInDTO.getEmailAddress());
        if (signUpDTOOptional.isPresent()) {
            SignUpDTO signUpDTO = signUpDTOOptional.get();
            if (signUpDTO.getCount() == 0) {
                return "registration/ResetPassword.jsp"; // Redirect to reset password page
            } else {
                Optional<SignUpDTO> validatedUser = signUpService.validateSignIn(signInDTO.getEmailAddress(), signInDTO.getPassword());
                if (validatedUser.isPresent()) {
                    if (validatedUser.get().isAccountLocked()) {
                        model.addAttribute("errorMessage", "Your account is locked. Please reset your password.");
//                        signUpDTO.setCount(0);
                        return "registration/SignIn.jsp";
                    } else {
                        String imageUrl="/images/"+validatedUser.get().getProfilePicture();
                        session.setAttribute("profileImage",imageUrl);
                        model.addAttribute("user", validatedUser.get());
                        model.addAttribute("userData",validatedUser.get());

                        Optional<List<ImageDownloadDTO>> imageDownloadDTO = signUpService.passImageDetails(validatedUser.get());
                        if (imageDownloadDTO.isPresent()){
                            System.out.println("Image has been found and ready to set" +imageDownloadDTO);
                            if (imageDownloadDTO.isPresent() && !imageDownloadDTO.get().isEmpty()) {
                                model.addAttribute("userImageData", imageDownloadDTO.get().get(0));
                            }
                        }
                        return "registration/Home.jsp"; // Redirect to home page if login is successful
                    }
                } else {
                    signUpService.handleFailedLoginAttempt(signUpDTO); // Update failed login attempts
                    if (signUpDTO.getFailedAttemptsCount() >= 3) {
                        signUpService.lockAccount(signUpDTO); // Lock account if failed attempts reach 3
                        model.addAttribute("errorMessage", "Your account is locked. Please reset your password.");
                        signUpDTO.setCount(0);
                        signUpDTO.setUpdatedPassword(null);
                        return "registration/SignIn.jsp";
                    } else {
                        model.addAttribute("errorMessage", "Invalid email or password");
                        model.addAttribute("attemptMessage", "Number of failed attempts: " + signUpDTO.getFailedAttemptsCount());
                        return "registration/SignIn.jsp";
                    }
                }
            }
        } else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "registration/SignIn.jsp";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("resetPasswordForm") ResetPasswordDTO resetPasswordDTO, Model model) {
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmNewPassword())) {
            model.addAttribute("errorMessage", "New Password and Confirm Password do not match.");
            return "registration/ResetPassword.jsp";
        }

        Optional<SignUpDTO> signUpDTOOptional = signUpService.findByEmailAddress(resetPasswordDTO.getEmailAddress());
        if (signUpDTOOptional.isPresent()) {
            SignUpDTO signUpDTO = signUpDTOOptional.get();
            signUpDTO.setPassword(resetPasswordDTO.getNewPassword());
            signUpService.updatePassword(signUpDTO); // Use updatePassword method to save the updated password

            model.addAttribute("successMessage", "Password reset successful. Please log in with your new password.");
            model.addAttribute("reset", true);
            return "registration/SignIn.jsp";
        } else {
            model.addAttribute("errorMessage", "User not found. Please try again.");
            return "registration/ResetPassword.jsp";
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid @ModelAttribute("dto") ForgetPasswordDTO forgetPasswordDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/ForgotPassword.jsp";
        }

        boolean isPasswordSent = signUpService.processForgetPassword(forgetPasswordDTO.getEmailAddress());
        if (isPasswordSent) {
            model.addAttribute("successMessage", "A new password has been successfully sent to your email address. Now please kindly go to SignIn page and login");
        } else {
            model.addAttribute("failureMessage", "The email address does not exist.");
        }

        return "registration/ForgotPassword.jsp";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // Get authenticated user's email from Spring Security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Assuming email is used as username

        // Fetch user details from service based on email
        Optional<SignUpDTO> signUpDTOOptional = signUpService.findByEmailAddress(userEmail);
        if (signUpDTOOptional.isPresent()) {
            SignUpDTO signUpDTO = signUpDTOOptional.get();

            // Create UserProfileDTO and populate with fetched details
            UserProfileDTO user = new UserProfileDTO();
            user.setProfilePicturePath(signUpDTO.getProfilePicturePath());
            user.setFirstName(signUpDTO.getFirstName());
            user.setLastName(signUpDTO.getLastName());

            model.addAttribute("user", user);

            return "registration/Edit.jsp"; // The name of your Thymeleaf template file (profile.html in your case)
        } else {
            // Handle scenario where user details are not found (unlikely in normal login flow)
            return "redirect:/login"; // Redirect to login page if user details are not found
        }
    }
}