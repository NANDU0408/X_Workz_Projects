package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.user.ImageDownloadDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.repository.SignUpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpRepo signUpRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

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

        // Encrypt the password before saving
        String password = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(password);
        signUpDTO.setPassword(encodedPassword);

        Optional<SignUpDTO> signUpDTOOptional = signUpRepo.save(signUpDTO);
        if (signUpDTOOptional.isPresent()) {
            signUpDTOOptional.get().setPassword(password);
            sendEmail(signUpDTOOptional.get());
        }

        return signUpDTOOptional;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return signUpRepo.checkEmailExists(email);
    }

    @Override
    public boolean checkPhoneNumberExists(String phoneNumber) {
        return signUpRepo.checkPhoneNumberExists(phoneNumber);
    }

    public void sendEmail(SignUpDTO signUpDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(signUpDTO.getEmailAddress());
        message.setSubject("One Time Password");
        message.setText("Dear "+signUpDTO.getFirstName()+" "+signUpDTO.getLastName()+" You have been successfully Signed Up,\n\n" +
                "Please Sign In through this password: "+signUpDTO.getPassword()+"\n\n" +
                "Thanks and Regards,\n"+" "+
                "XworkzProject Team");
        emailSender.send(message);
    }


    @Override
    @Transactional
    public Optional<SignUpDTO> validateSignIn(String emailAddress, String password) {
        Optional<SignUpDTO> signUpDTOOptional = signUpRepo.findByEmailAddress(emailAddress);
        if (signUpDTOOptional.isPresent()) {
            SignUpDTO signUpDTO = signUpDTOOptional.get();
            if (passwordEncoder.matches(password,signUpDTO.getPassword()) ||
                    (passwordEncoder.matches(password,signUpDTO.getUpdatedPassword()))) {
                signUpDTO.setFailedAttemptsCount(0); // Reset failed attempts on successful login
                signUpDTO.setFailedAttemptDateTime(null); // Reset failed attempt datetime
                signUpRepo.merge(signUpDTO); // Save the updated SignUpDTO object
                return Optional.of(signUpDTO);
            } else {
                handleFailedLoginAttempt(signUpDTO); // Update failed login attempts
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void handleFailedLoginAttempt(SignUpDTO signUpDTO) {
        // Perform your logic to handle failed login attempt
        if (signUpDTO.getFailedAttemptDateTime() == null ||
                signUpDTO.getFailedAttemptDateTime().plusHours(1).isBefore(LocalDateTime.now())) {
            signUpDTO.setFailedAttemptsCount(1);
        } else {
            signUpDTO.setFailedAttemptsCount(signUpDTO.getFailedAttemptsCount() + 1);
        }

        signUpDTO.setFailedAttemptDateTime(LocalDateTime.now());
        if (signUpDTO.getFailedAttemptsCount() >= 3) {
            signUpDTO.setAccountLocked(true);
        }

        // Save the managed entity
        signUpRepo.merge(signUpDTO);
    }


    @Override
    @Transactional
    public void lockAccount(SignUpDTO signUpDTO) {
        signUpDTO.setAccountLocked(true);
        signUpDTO.setCount(0);
        signUpDTO.setUpdatedPassword(null);
        signUpRepo.merge(signUpDTO);
    }



    @Override
    public Optional<AdminDTO> validateAdminSignIn(String emailAddress, String password) {
        Optional<AdminDTO> adminDTOOptional = signUpRepo.findByAdminEmailAddress(emailAddress);
        if (adminDTOOptional.isPresent()) {
            AdminDTO adminDTO = adminDTOOptional.get();
            if (adminDTO.getPassword().equals(password)) {
                // Additional checks or actions can be added here if needed
                return Optional.of(adminDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<SignUpDTO> findByEmailAddress(String emailAddress) {
        return signUpRepo.findByEmailAddress(emailAddress);
    }

    @Override
    public void updateCount(SignUpDTO signUpDTO) {
        signUpRepo.save(signUpDTO);
    }

    @Override
    public void updatePassword(SignUpDTO signUpDTO) {
        System.out.println("Service update by password "+signUpDTO);
        sendEmail(signUpDTO);
        signUpDTO.setUpdatedPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        signUpRepo.updatePassword(signUpDTO);
    }

    @Override
    public boolean processForgetPassword(String emailAddress) {
        Optional<SignUpDTO> optionalSignUpDTO = signUpRepo.findByEmailAddress(emailAddress);
        if (optionalSignUpDTO.isPresent()) {
            SignUpDTO signUpDTO = optionalSignUpDTO.get();
            String newPassword = signUpRepo.generateRandomPassword();

            signUpDTO.setPassword(passwordEncoder.encode(newPassword)); // Update the password field directly
            signUpDTO.setFailedAttemptsCount(0);
            signUpDTO.setCount(0);
            signUpDTO.setFailedAttemptDateTime(null);
            signUpDTO.setAccountLocked(false);
            System.out.println("Running forgetPassword = " +newPassword);

            Optional<SignUpDTO> signUpDTOOptional =  signUpRepo.merge(signUpDTO); // Save the changes
            System.out.println(signUpDTOOptional.get());
            System.out.println("Running forgetPassword = " +newPassword);

            sendPasswordEmail(signUpDTO.getEmailAddress(), newPassword);
            return true;
        }
        return false;
    }

    private void sendPasswordEmail(String emailAddress, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Your New Password");
//        message.setText("Your new password is: " + newPassword);
        message.setText("Your new password is: " + newPassword+"\n Now please login through this password and reset your password");
        emailSender.send(message);
    }

    @Override
    public List<SignUpDTO> getAllSignUpDetails() {
        return signUpRepo.findAll();
    }

    @Override
    @Transactional
    public SignUpDTO updateUserDetails(String emailAddress, SignUpDTO signUpDTO) {
        System.out.println("service updateUserDetails process is initiated.");

        Optional<SignUpDTO> optionalUser = signUpRepo.findByEmailAddress(emailAddress);
        if (optionalUser.isPresent()) {
            SignUpDTO existingUser = optionalUser.get();
            existingUser.setFirstName(signUpDTO.getFirstName());
            existingUser.setLastName(signUpDTO.getLastName());
            existingUser.setMobileNumber(signUpDTO.getMobileNumber());
            existingUser.setProfilePicturePath(signUpDTO.getProfilePicturePath());

            return signUpRepo.merge(existingUser).get();
        } else {
            return null;
        }
    }


    @Override
    public void saveImageDetails(ImageDownloadDTO imageDownloadDTO) {
        System.out.println("Running saveImageDetails in signInService"+imageDownloadDTO);
        signUpRepo.mergeImage(imageDownloadDTO);
    }

    public Optional<List<ImageDownloadDTO>> passImageDetails(SignUpDTO signUpDTO){
        System.out.println("Running passImageDetails in signInService" +signUpDTO);
        return signUpRepo.findImage(signUpDTO.getId());
    }

    @Override
    public List<ImageDownloadDTO> findByUserIdAndStatus(int userId, String status) {
        System.out.println("Running findByUserIdAndStatus in service");
        return signUpRepo.findByUserIdAndStatus(userId, status);
    }

    public boolean updateProfile(SignUpDTO updatedProfile) {
        Optional<SignUpDTO> existingProfileOptional = signUpRepo.findByEmailAddress(updatedProfile.getEmailAddress());
        if (existingProfileOptional.isPresent()) {
            SignUpDTO existingProfile = existingProfileOptional.get();
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setLastName(updatedProfile.getLastName());
            existingProfile.setMobileNumber(updatedProfile.getMobileNumber());
            if (updatedProfile.getProfilePicture() != null) {
                existingProfile.setProfilePicture(updatedProfile.getProfilePicture());
            }
            signUpRepo.save(existingProfile);
            return true;
        }
        return false;
    }

    public String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }

}