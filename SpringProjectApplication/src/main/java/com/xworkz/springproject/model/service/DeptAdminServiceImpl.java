package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptAdminServiceImpl implements DeptAdminService{

    @Autowired
    DeptService deptService;

    @Override
    public boolean assignDeptAndStatusForDeptAdmin(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning department and status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        deptService.savedeptIdAnddeptNameForDeptAdmin(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus(), requestToDeptAndStatusOfComplaintDto.getEmpId());

        return true;
    }

    @Override
    public boolean assignDeptAndStatusForDeptHistory(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning department and status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        deptService.savedeptIdAnddeptNameForDeptHistory(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus(), requestToDeptAndStatusOfComplaintDto.getEmpId());

        return true;
    }
}
