package com.xworkz.springproject.model.service;


import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;

public interface AdminService {
    public boolean assignDeptAndStatus(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);
}
