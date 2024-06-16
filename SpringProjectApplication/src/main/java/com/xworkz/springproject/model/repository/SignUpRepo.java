package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface SignUpRepo {

    default public Optional<SignUpDTO> save(SignUpDTO signUpDTO){
        return Optional.empty();
    }

    Optional<SignUpDTO> merge(SignUpDTO signUpDTO);

    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);

    Optional<SignUpDTO> findByEmailAddress(String emailAddress);

    boolean updatePassword(SignUpDTO signUpDTO);

    Optional<AdminDTO> findByAdminEmailAddress(String emailAddress);

    public List<SignUpDTO> findAll();

    String generateRandomPassword();


}
