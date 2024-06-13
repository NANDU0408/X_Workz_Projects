package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpService {

    default Optional<SignUpDTO> save(SignUpDTO signUpDTO){
        return Optional.empty();
    }


    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);

    void sendEmail(SignUpDTO signUpDTO);

    Optional<SignUpDTO> validateSignIn(String emailAddress, String password);

    Optional<SignUpDTO> findByEmailAddress(String emailAddress); // Add this method

    void updateCount(SignUpDTO signUpDTO);

    void updatePassword(SignUpDTO signUpDTO);

}
