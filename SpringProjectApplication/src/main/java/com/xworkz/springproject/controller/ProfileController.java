package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/updateRest")
    public ResponseEntity<String> updateProfile(@RequestBody SignUpDTO signUpDTO) {
        boolean updated = signUpService.updateProfile(signUpDTO);
        if (updated) {
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update profile");
        }
    }

    @GetMapping("/email")
    public boolean checkEmail(@RequestParam String email) {
        return signUpService.checkEmailExists(email);
    }

    @GetMapping("/phone")
    public boolean checkPhone(@RequestParam String phone) {
        return signUpService.checkPhoneNumberExists(phone);
    }
}