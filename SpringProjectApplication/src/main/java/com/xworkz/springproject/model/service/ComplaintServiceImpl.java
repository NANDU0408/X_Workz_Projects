package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.repository.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintRepo complaintRepo;

    @Override
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO) {
        System.out.println("Running saveComplaint in ComplaintServiceImpl"+raiseComplaintDTO);
        return complaintRepo.saveComplaint(raiseComplaintDTO, signUpDTO);
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaintsByUserId(int userId) {
        System.out.println("Running findAllComplaintsByUserId in ComplaintServiceImpl");
        return complaintRepo.findAllComplaintsByUserId(userId);
    }
}
