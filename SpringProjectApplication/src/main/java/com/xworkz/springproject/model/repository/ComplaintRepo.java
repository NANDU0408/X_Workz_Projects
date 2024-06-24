package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepo  {
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO);

    public List<RaiseComplaintDTO> findAllComplaints(int userId);

    public Optional<RaiseComplaintDTO> findComplaintById(int complaintId);

    List<RaiseComplaintDTO> findByUserIdAndStatus(int userId, String status);

}