package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.model.repository.DeptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptRepo deptRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Optional<DeptAdminDTO> validateDeptAdminSignIn(String emailAddress, String password) {
        Optional<DeptAdminDTO> deptAdminDTOOptional = deptRepo.findByDeptAdminEmailAddress(emailAddress);
        if (deptAdminDTOOptional.isPresent()) {
            DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();
            if (deptAdminDTO.getPassword().equals(password)) {
                // Additional checks or actions can be added here if needed
                return Optional.of(deptAdminDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeRegisterDTO> saveEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        if (deptRepo.checkEmailExists(employeeRegisterDTO.getEmailAddress())) {
            System.out.println("Email already exists");
            return Optional.empty();
        }
        if (deptRepo.checkPhoneNumberExists(employeeRegisterDTO.getMobileNumber())) {
            System.out.println("Phone number already exists");
            return Optional.empty();
        }
        return deptRepo.saveEmp(employeeRegisterDTO);
    }

    @Override
    public boolean checkEmailExists(String email) {

        return deptRepo.checkEmailExists(email);
    }

    @Override
    public boolean checkPhoneNumberExists(String phoneNumber) {
        return deptRepo.checkPhoneNumberExists(phoneNumber);
    }

    public void sendEmailEmp(EmployeeRegisterDTO employeeRegisterDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employeeRegisterDTO.getEmailAddress());
        message.setSubject("Employee Credentials");
        message.setText("Dear "+employeeRegisterDTO.getFirstName()+" "+employeeRegisterDTO.getLastName()+" You have been successfully Registered by Department Admin,\n\n" +
                "Please Sign In through this password: "+employeeRegisterDTO.getPassword()+"\n\n" +
                "Thanks and Regards,\n"+" "+
                "XworkzProject Team");
        emailSender.send(message);
    }

    @Override
    @Transactional
    public Optional<EmployeeRegisterDTO> validateSignInEmp(String emailAddress, String password) {
        Optional<EmployeeRegisterDTO> employeeRegisterDTOOptional = deptRepo.findByEmpEmailAddress(emailAddress);
        if (employeeRegisterDTOOptional.isPresent()) {
            EmployeeRegisterDTO employeeRegisterDTO = employeeRegisterDTOOptional.get();
            if (employeeRegisterDTO.getPassword().equals(password)) {

                return Optional.of(employeeRegisterDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeRegisterDTO> findByEmpEmailAddress(String emailAddress) {
        return deptRepo.findByEmpEmailAddress(emailAddress);
    }

    @Override
    public Optional<List<EmployeeRegisterDTO>> findEmpoloyeeByDeptId(int deptId) {
        System.out.println("service Find Employee by dept id "+ deptId);
        return deptRepo.findEmployeeByDeptId(deptId);
    }

    @Override
    public List<EmployeeRegisterDTO> getDeptIdAndDeptNameForDept() {
        return deptRepo.getdeptIdAnddeptNameForDept();
    }

    @Override
    public List<RaiseComplaintDTO> findAllComplaintsForDeptAdmin() {
        System.out.println("Running findAllComplaintsForAdmin in DeptServiceImpl");
        return deptRepo.findAllComplaintsForDeptAdmin();
    }

    @Override
    public boolean savedeptIdAnddeptNameForDeptAdmin(int complaintId, int deptId, String complaintStatus,String assignEmployee) {
        return deptRepo.savedeptIdAnddeptNameForDeptAdmin(complaintId,deptId,complaintStatus,assignEmployee);
    }

    @Override
    public boolean savedeptIdAnddeptNameForDeptHistory(int complaintId, int deptId, String complaintStatus, String assignEmployee) {
        return deptRepo.savedeptIdAnddeptNameForDeptHistory(complaintId,deptId,complaintStatus,assignEmployee);
    }

    @Override
    public Optional<RaiseComplaintDTO> saveHistoryForDept(HistoryDTO historyDTO, RaiseComplaintDTO raiseComplaintDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto){
        return deptRepo.saveHistoryForDept(historyDTO,raiseComplaintDTO,requestToDeptAndStatusOfComplaintDto);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(String complaintType, String city, String complaintStatus) {
        return deptRepo.searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(complaintType, city,complaintStatus);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(String complaintType, String complaintStatus){
        return deptRepo.searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(complaintType,complaintStatus);
    }
    @Override
    public List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForAdmin(String city, String complaintStatus){
        return deptRepo.searchComplaintsCityAndComplaintStatusForAdmin(city,complaintStatus);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForAdmin(String complaintType, String city){
        return deptRepo.searchComplaintsBycomplaintTypeAndCityForAdmin(complaintType, city);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForAdminDept(String complaintType, String city, String complaintStatus) {
        System.out.println("Running searchComplaintsBycomplaintTypeOrcityForAdmin in DeptServiceImpl");
        return deptRepo.searchComplaintsBycomplaintTypeOrcityForDept(complaintType,city,complaintStatus);
    }

    @Override
    public List<HistoryDTO> findCompaintHistoryByComplaintId(HistoryDTO historyDTO) {
        System.out.println("Running findCompaintHistoryByComplaintId in DeptServiceImpl");
        return deptRepo.findComplaintHistoryByComplaintId(historyDTO);
    }
}
