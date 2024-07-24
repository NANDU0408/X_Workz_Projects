package com.xworkz.springproject.model.repository;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DeptRepo {

    Optional<DeptAdminDTO> findByDeptAdminEmailAddress(String emailAddress);

    @Transactional
    Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO);

    boolean updateEmpPassword(EmployeeRegisterDTO employeeRegisterDTO);

    boolean checkEmailExists(String emailAddress);

    boolean checkPhoneNumberExists(String mobileNumber);

    Optional<EmployeeRegisterDTO> findByEmpEmailAddress(String emailAddress);

    Optional<List<EmployeeRegisterDTO>> findEmployeeByDeptId(int deptId);

    List<EmployeeRegisterDTO> getdeptIdAnddeptNameForDept();

    List<RaiseComplaintDTO> findAllComplaintsForDeptAdmin(String deptAssign);

    boolean savedeptIdAnddeptNameForDeptAdmin(int complaintId, int deptId, String complaintStatus,String assignEmployee);

    boolean savedeptIdAnddeptNameForDeptEmp(int complaintId, int deptId, String complaintStatus);

    boolean savedeptIdAnddeptNameForDeptHistory(int complaintId, int deptId, String complaintStatus, String assignEmployee);

    @Transactional
    Optional<RaiseComplaintDTO> saveHistoryForDept(HistoryDTO historyDTO, RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto);

    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(String complaintType, String city, String complaintStatus);

    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(String complaintType,String complaintStatus);

    public List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForAdmin(String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForAdmin(String complaintType, String city);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForDept(String complaintType, String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForEmp(String complaintType, String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForEmp(String complaintType, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForEmp(String city, String complaintStatus);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForEmp(String complaintType, String city);

    List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForDeptEmp(String complaintType, String city, String complaintStatus);

    List<HistoryDTO> findComplaintHistoryByComplaintId(HistoryDTO historyDTO);

    List<WaterDeptDTO> getAllDepartments();

    @Transactional
    Optional<EmployeeRegisterDTO> mergeEmp(EmployeeRegisterDTO employeeRegisterDTO);

    @Transactional
    Optional<DeptAdminDTO> mergeDeptAdmin(DeptAdminDTO deptAdminDTO);

    public String findByDeptName(int departmentId);

    String generateRandomPassword();
}
