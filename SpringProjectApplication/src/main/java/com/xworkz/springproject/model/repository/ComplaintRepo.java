package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepo  {
    public Optional<RaiseComplaintDTO> saveComplaint(RaiseComplaintDTO raiseComplaintDTO, SignUpDTO signUpDTO);

    public List<RaiseComplaintDTO> findAllComplaints(int userId);

    List<RaiseComplaintDTO> findAllComplaintsForAdmin();

    public Optional<RaiseComplaintDTO> findComplaintById(int complaintId);

    List<RaiseComplaintDTO> findByUserIdAndStatus(int userId, String status);

    List<RaiseComplaintDTO> findByUserStatusForAdmin(String status);

    Optional<RaiseComplaintDTO> findById(int complaintId);

    @Transactional
    Optional<RaiseComplaintDTO> mergeDescription(RaiseComplaintDTO raiseComplaintDTO);

    List<RaiseComplaintDTO> searchComplaintsByType(String keyword);

    List<RaiseComplaintDTO> searchComplaintsByCity(String keyword);

    List<RaiseComplaintDTO> searchComplaintsByUpdatedDate(String keyword);

    List<RaiseComplaintDTO> searchComplaintsByTypeForAdmin(String keyword);

    List<RaiseComplaintDTO> searchComplaintsByCityForAdmin(String keyword);

    List<RaiseComplaintDTO> searchComplaintsByUpdatedDateForAdmin(String keyword);
}