package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.model.repository.DeptAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class DeptAdminServiceImpl implements DeptAdminService{

    @Autowired
    DeptService deptService;

    @Autowired
    DeptAdminRepo deptAdminRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

    @Override
    public boolean assignDeptAndStatusForDeptAdmin(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning department and status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        deptService.savedeptIdAnddeptNameForDeptAdmin(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus(), requestToDeptAndStatusOfComplaintDto.getEmpId());

        return true;
    }

    @Override
    public boolean assignDeptAndStatusForDeptEmp(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        deptService.savedeptIdAnddeptNameForDeptEmp(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus());

        return true;
    }

    @Override
    public boolean assignDeptAndStatusForDeptHistory(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning department and status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        deptService.savedeptIdAnddeptNameForDeptHistory(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus(), requestToDeptAndStatusOfComplaintDto.getEmpId());

        return true;
    }

    @Override
    public Optional<DeptAdminDTO> saveDeptAdmin(DeptAdminDTO deptAdminDTO) {
        if (deptAdminRepo.checkDeptAdminEmailExists(deptAdminDTO.getEmailAddress())) {
            System.out.println("Email already exists");
            return Optional.empty();
        }
        if (deptAdminRepo.checkDeptAdminPhoneNumberExists(deptAdminDTO.getMobileNumber())) {
            System.out.println("Phone number already exists");
            return Optional.empty();
        }

        // Encrypt the password before saving
        String password = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(password);
        deptAdminDTO.setPassword(encodedPassword);

        System.out.println("Dept admin creation "+deptAdminDTO);
        System.out.println(password);

        Optional<DeptAdminDTO> deptAdminDTOOptional = deptAdminRepo.saveDeptAdmin(deptAdminDTO);
        if (deptAdminDTOOptional.isPresent()) {
            deptAdminDTOOptional.get().setPassword(password);
            sendEmailDeptAdmin(deptAdminDTOOptional.get());
        }

        return deptAdminDTOOptional;
    }

    @Override
    public boolean checkDeptAdminEmailExists(String email) {

        return deptAdminRepo.checkDeptAdminEmailExists(email);
    }

    @Override
    public boolean checkDeptAdminPhoneNumberExists(String phoneNumber) {
        return deptAdminRepo.checkDeptAdminPhoneNumberExists(phoneNumber);
    }

    public void sendEmailDeptAdmin(DeptAdminDTO deptAdminDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(deptAdminDTO.getEmailAddress());
        message.setSubject("Employee Credentials");
        message.setText("Dear "+deptAdminDTO.getFirstName()+" "+deptAdminDTO.getLastName()+" You have been successfully Registered by Department Admin,\n\n" +
                "Please Sign In through this email: "+deptAdminDTO.getEmailAddress()+ " and password: "+deptAdminDTO.getPassword()+"\n\n" +
                "Thanks and Regards,\n"+" "+
                "XworkzProject Team");
        emailSender.send(message);
    }

    @Override
    public Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress) {
        return deptAdminRepo.findByDeptAdminEmailAddress(emailAddress);
    }

    @Override
    public void updateDeptAdminPassword(DeptAdminDTO deptAdminDTO) {
        try{

        System.out.println("Service update by password "+deptAdminDTO);
        sendEmailDeptAdmin(deptAdminDTO);
        System.out.println(deptAdminDTO);
        deptAdminDTO.setPassword(passwordEncoder.encode(deptAdminDTO.getPassword()));
        deptAdminDTO.setFailedAttemptsCount(0);
        deptAdminDTO.setFailedAttemptDateTime(null);
        deptAdminRepo.updateDeptAdminPassword(deptAdminDTO);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
