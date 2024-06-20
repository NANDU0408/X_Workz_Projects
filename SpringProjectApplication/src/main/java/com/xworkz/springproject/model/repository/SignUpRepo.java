package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.user.ImageDownloadDTO;
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

    public SignUpDTO updateProfile(SignUpDTO signUpDTO);

    public void saveImageDetails(ImageDownloadDTO imageDownloadDTO);

    public Optional<ImageDownloadDTO> mergeImage(ImageDownloadDTO imageDownloadDTO);

    String generateRandomPassword();

    public Optional<List<ImageDownloadDTO>> findImage(int userId);

    public List<ImageDownloadDTO> findByUserIdAndStatus(int userId, String status);


}
