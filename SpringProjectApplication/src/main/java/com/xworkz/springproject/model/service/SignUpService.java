package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.user.ImageDownloadDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import java.util.List;
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

    Optional<AdminDTO> validateAdminSignIn(String emailAddress, String password);

//    void updateFailedLoginAttempts(SignUpDTO signUpDTO);

    public void handleFailedLoginAttempt(SignUpDTO signUpDTO);

    public boolean processForgetPassword(String emailAddress);

    List<SignUpDTO> getAllSignUpDetails();


    void lockAccount(SignUpDTO signUpDTO);

    public SignUpDTO updateUserDetails(String emailAddress, SignUpDTO signUpDTO);

    public void saveImageDetails(ImageDownloadDTO imageDownloadDTO);

    public Optional<List<ImageDownloadDTO>> passImageDetails(SignUpDTO signUpDTO);

    List<ImageDownloadDTO> findByUserIdAndStatus(int id, String active);

    ;
}
