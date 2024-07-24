package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;

import java.util.Optional;

public interface DeptAdminService {
    boolean assignDeptAndStatusForDeptAdmin(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    boolean assignDeptAndStatusForDeptEmp(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    boolean assignDeptAndStatusForDeptHistory(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    Optional<DeptAdminDTO> saveDeptAdmin(DeptAdminDTO deptAdminDTO);

    boolean checkDeptAdminEmailExists(String email);

    boolean checkDeptAdminPhoneNumberExists(String phoneNumber);

    Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress);

    void updateDeptAdminPassword(DeptAdminDTO deptAdminDTO);
}
