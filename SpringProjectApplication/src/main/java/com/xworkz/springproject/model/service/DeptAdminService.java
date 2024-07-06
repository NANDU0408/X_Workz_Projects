package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;

public interface DeptAdminService {
    boolean assignDeptAndStatusForDeptAdmin(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    boolean assignDeptAndStatusForDeptHistory(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);
}
