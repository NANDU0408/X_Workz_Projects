package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.SignUpDTO;
import com.xworkz.springproject.model.repository.SignUpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpRepo signUpRepo;

    @Autowired
    private JavaMailSender emailSender;

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
    public Optional<SignUpDTO> validateSignIn(String emailAddress, String password) {
        Optional<SignUpDTO> signUpDTOOptional = signUpRepo.findByEmailAddress(emailAddress);
        if (signUpDTOOptional.isPresent()) {
            SignUpDTO signUpDTO = signUpDTOOptional.get();
            if (signUpDTO.getPassword().equals(password) || (signUpDTO.getUpdatedPassword() != null && signUpDTO.getUpdatedPassword().equals(password))) {
                if (signUpDTO.getCount() == 0) {
                    signUpDTO.setCount(1);
                    signUpRepo.save(signUpDTO); // Update the count in the database
                    return Optional.of(signUpDTO);
                }
                return Optional.of(signUpDTO);
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
        signUpRepo.updatePassword(signUpDTO);
    }
}