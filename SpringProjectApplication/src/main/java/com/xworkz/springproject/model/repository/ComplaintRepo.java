package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepo {
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO,SignUpDTO signUpDTO);

    List<RaiseComplaintDTO> findAllComplaintsByUserId(int userId);
}
