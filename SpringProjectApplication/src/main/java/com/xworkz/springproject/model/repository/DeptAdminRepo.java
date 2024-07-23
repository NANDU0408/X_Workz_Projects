package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DeptAdminRepo {

    boolean checkDeptAdminEmailExists(String emailAddress);

    boolean checkDeptAdminPhoneNumberExists(String mobileNumber);

    @Transactional
    Optional<DeptAdminDTO> saveDeptAdmin(DeptAdminDTO deptAdminDTO);

    Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress);

    boolean updateDeptAdminPassword(DeptAdminDTO deptAdminDTO);
}
