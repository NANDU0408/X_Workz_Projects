package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.repository.ComplaintRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService{

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO) {
        System.out.println("Running saveComplaint in ComplaintServiceImpl"+raiseComplaintDTO);
        return complaintRepo.saveComplaint(raiseComplaintDTO, signUpDTO);
    }

    public void sendEmail(SignUpDTO signUpDTO, RaiseComplaintDTO raiseComplaintDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(signUpDTO.getEmailAddress());
        message.setSubject("Complaint Registration Information");
        message.setText("Dear "+signUpDTO.getFirstName()+" "+signUpDTO.getLastName()+" You have been successfully registered the complaint,\n\n" +
                "Your complaint ID is "+signUpDTO.getPassword()+"\n\n" +
                "Please Sign In through this password: "+
                "Thanks and Regards,\n"+" "+
                "XworkzProject Team");
        emailSender.send(message);
    }



    @Override
    public List<RaiseComplaintDTO> findAllComplaints(int userId) {
        System.out.println("Running findAllComplaints in ComplaintServiceImpl");
        return complaintRepo.findAllComplaints(userId);
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaintsForAdmin() {
        System.out.println("Running findAllComplaintsForAdmin in ComplaintServiceImpl");
        return complaintRepo.findAllComplaintsForAdmin();
    }

    @Override
    public Optional<RaiseComplaintDTO> findComplaintById(int complaintId) {
        System.out.println("Running findComplaintById in ComplaintServiceImpl");
        return complaintRepo.findComplaintById(complaintId);
    }

    @Override
    public List<RaiseComplaintDTO> findComplaintsByStatus(int userId, String status) {
        return complaintRepo.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<RaiseComplaintDTO> findComplaintsByStatusForAdmin(String status) {
        return complaintRepo.findByUserStatusForAdmin(status);
    }

    @Override
    public Optional<RaiseComplaintDTO> findById(int complaintId) {
        return complaintRepo.findById(complaintId);
    }

    @Override
    @Transactional
    public Optional<RaiseComplaintDTO> mergeDescription(RaiseComplaintDTO raiseComplaintDTO) {
        return complaintRepo.mergeDescription(raiseComplaintDTO);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByType(String keyword) {
        return complaintRepo.searchComplaintsByType(keyword);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByCity(String keyword) {
        return complaintRepo.searchComplaintsByCity(keyword);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByUpdatedDate(String keyword) {
        return complaintRepo.searchComplaintsByUpdatedDate(keyword);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByTypeForAdmin(String keyword) {
        return complaintRepo.searchComplaintsByTypeForAdmin(keyword);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByCityForAdmin(String keyword) {
        return complaintRepo.searchComplaintsByCityForAdmin(keyword);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsByUpdatedDateForAdmin(String keyword) {
        return complaintRepo.searchComplaintsByUpdatedDateForAdmin(keyword);
    }
}
