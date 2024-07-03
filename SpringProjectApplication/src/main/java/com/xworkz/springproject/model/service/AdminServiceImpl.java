package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private ComplaintService complaintService;

    @Override
    public boolean assignDeptAndStatus(RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto) {
        System.out.println("working on assigning department and status to complaint"+ requestToDeptAndStatusOfComplaintDto);
        complaintService.savedeptIdAnddeptName(requestToDeptAndStatusOfComplaintDto.getComplaintId(),requestToDeptAndStatusOfComplaintDto.getDepartmentId(),requestToDeptAndStatusOfComplaintDto.getComplaintStatus());

        return true;
    }
}
