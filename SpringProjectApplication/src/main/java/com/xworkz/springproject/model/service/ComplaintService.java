package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintService {

    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO);

    List<RaiseComplaintDTO> findAllComplaints(int userId);

    public Optional<RaiseComplaintDTO> findComplaintById(int complaintId);

    List<RaiseComplaintDTO> findComplaintsByStatus(int userId, String status);

}