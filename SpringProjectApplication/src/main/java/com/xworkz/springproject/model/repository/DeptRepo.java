package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DeptRepo {

    Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress);

    @Transactional
    Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO);

    boolean checkEmailExists(String emailAddress);

    boolean checkPhoneNumberExists(String mobileNumber);
}
