package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.SignUpDTO;

import java.util.Optional;

public interface SignUpRepo {

    default public Optional<SignUpDTO> save(SignUpDTO signUpDTO){
        return Optional.empty();
    }

    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);

    Optional<SignUpDTO> findByEmailAddress(String emailAddress);

    boolean updatePassword(SignUpDTO signUpDTO);
}
