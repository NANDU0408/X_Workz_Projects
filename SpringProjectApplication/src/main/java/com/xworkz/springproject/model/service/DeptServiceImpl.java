package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.model.repository.DeptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptRepo deptRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Optional<DeptAdminDTO> validateDeptAdminSignIn(String emailAddress, String password) {
        Optional<DeptAdminDTO> deptAdminDTOOptional = deptRepo.findByDeptAdminEmailAddress(emailAddress);
        if (deptAdminDTOOptional.isPresent()) {
            DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();
            if (deptAdminDTO.getPassword().equals(password)) {
                // Additional checks or actions can be added here if needed
                return Optional.of(deptAdminDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        if (deptRepo.checkEmailExists(employeeRegisterDTO.getEmailAddress())) {
            System.out.println("Email already exists");
            return Optional.empty();
        }
        if (deptRepo.checkPhoneNumberExists(employeeRegisterDTO.getMobileNumber())) {
            System.out.println("Phone number already exists");
            return Optional.empty();
        }
        return deptRepo.saveEmp(employeeRegisterDTO);
    }

    @Override
    public boolean checkEmailExists(String email) {

        return deptRepo.checkEmailExists(email);
    }

    @Override
    public boolean checkPhoneNumberExists(String phoneNumber) {
        return deptRepo.checkPhoneNumberExists(phoneNumber);
    }

    public void sendEmailEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employeeRegisterDTO.getEmailAddress());
        message.setSubject("Employee Credentials");
        message.setText("Dear "+employeeRegisterDTO.getFirstName()+" "+employeeRegisterDTO.getLastName()+" You have been successfully Registered by Department Admin,\n\n" +
                "Please Sign In through this password: "+employeeRegisterDTO.getPassword()+"\n\n" +
                "Thanks and Regards,\n"+" "+
                "XworkzProject Team");
        emailSender.send(message);
    }
}
