package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
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

    List<RaiseComplaintDTO> searchComplaintsByTypeForAdmin(String complaintType);

    List<RaiseComplaintDTO> searchComplaintsByCityForAdmin(String city);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndcityForAdmin(String complaintType, String city);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForAdmin(String complaintType, String city);

    List<RaiseComplaintDTO> findAllComplaints();

    public List<WaterDeptDTO> getdeptIdAnddeptName();

    public boolean savedeptIdAnddeptName(int complaintId,int deptId,String complaintStatus);

    @Transactional
    Optional<RaiseComplaintDTO> saveHistory(HistoryDTO historyDTO,RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);
}