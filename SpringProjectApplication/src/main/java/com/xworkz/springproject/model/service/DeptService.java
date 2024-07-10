package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DeptService {

    Optional<DeptAdminDTO> validateDeptAdminSignIn(String emailAddress, String password);

    Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO);

    boolean checkEmailExists(String email);

    boolean checkPhoneNumberExists(String phoneNumber);

    void sendEmailEmp(EmployeeRegisterDTO employeeRegisterDTO);

    @Transactional
    Optional<EmployeeRegisterDTO> validateSignInEmp(String emailAddress, String password);

    void updateEmpPassword(EmployeeRegisterDTO employeeRegisterDTO);

    boolean processForgetEmpPassword(String emailAddress);

    Optional<EmployeeRegisterDTO> findByEmpEmailAddress(String emailAddress);

    Optional<List<EmployeeRegisterDTO>> findEmpoloyeeByDeptId(int deptId);

    List<EmployeeRegisterDTO> getDeptIdAndDeptNameForDept();

    List<RaiseComplaintDTO> findAllComplaintsForDeptAdmin();

    boolean savedeptIdAnddeptNameForDeptAdmin(int complaintId, int deptId, String complaintStatus,String assignEmployee);

    boolean savedeptIdAnddeptNameForDeptHistory(int complaintId, int deptId, String complaintStatus, String assignEmployee);

    Optional<RaiseComplaintDTO> saveHistoryForDept(HistoryDTO historyDTO, RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(String complaintType, String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(String complaintType, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForAdmin(String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForAdmin(String complaintType, String city);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForAdminDept(String complaintType, String city, String complaintStatus);

    List<HistoryDTO> findCompaintHistoryByComplaintId(HistoryDTO historyDTO);

    List<WaterDeptDTO> getAllDepartments();
}
