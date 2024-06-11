package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpService {

    default Optional<SignUpDTO> save(SignUpDTO signUpDTO){
        return Optional.empty();
    }


    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);
}
