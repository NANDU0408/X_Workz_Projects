package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;

import java.util.Optional;

public interface DeptService {

    Optional<DeptAdminDTO> validateDeptAdminSignIn(String emailAddress, String password);

    Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO);

    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);

    void sendEmailEmp(EmployeeRegisterDTO employeeRegisterDTO);
}
