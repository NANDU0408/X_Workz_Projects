package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.SignUpDTO;
import com.xworkz.springproject.model.repository.SignUpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpRepo signUpRepo;

    @Override
    public Optional<SignUpDTO> save(SignUpDTO signUpDTO) {
        if (signUpRepo.checkEmailExists(signUpDTO.getEmailAddress())) {
            System.out.println("Email already exists");
            return Optional.empty();
        }
        if (signUpRepo.checkPhoneNumberExists(signUpDTO.getMobileNumber())) {
            System.out.println("Phone number already exists");
            return Optional.empty();
        }
        return signUpRepo.save(signUpDTO);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return signUpRepo.checkEmailExists(email);
    }

    @Override
    public boolean checkPhoneNumberExists(String phoneNumber) {
        return signUpRepo.checkPhoneNumberExists(phoneNumber);
    }
}