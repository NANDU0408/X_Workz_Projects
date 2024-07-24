package com.xworkz.springproject.model.service;

import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.responseDto.ResponseHistoryDTO;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.model.repository.ComplaintRepo;
import com.xworkz.springproject.model.repository.DeptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptRepo deptRepo;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 16;

    @Override
    public Optional<DeptAdminDTO> validateDeptAdminSignIn(String emailAddress, String password) {
        Optional<DeptAdminDTO> deptAdminDTOOptional = deptRepo.findByDeptAdminEmailAddress(emailAddress);
        if (deptAdminDTOOptional.isPresent()) {
            DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();
//            if (deptAdminDTO.getPassword().equals(password)) {
            if (passwordEncoder.matches(password,deptAdminDTO.getPassword())) {
                deptAdminDTO.setFailedAttemptsCount(0); // Reset failed attempts on successful login
                deptAdminDTO.setFailedAttemptDateTime(null); // Reset failed attempt datetime
                deptRepo.mergeDeptAdmin(deptAdminDTO);

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

        // Encrypt the password before saving
        String password = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(password);
        employeeRegisterDTO.setPassword(encodedPassword);

        Optional<EmployeeRegisterDTO> employeeRegisterDTO1 = deptRepo.saveEmp(employeeRegisterDTO);
        if (employeeRegisterDTO1.isPresent()) {
            employeeRegisterDTO1.get().setPassword(password);
            sendEmailEmp(employeeRegisterDTO1.get());
        }

        return employeeRegisterDTO1;
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
                "Please Sign In through this email: "+employeeRegisterDTO.getEmailAddress()+ " and password: "+employeeRegisterDTO.getPassword()+"\n\n" +
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
//            System.out.println("emp stored pass "+employeeRegisterDTO.getPassword());
//
//            System.out.println("plaint password "+password);
//            System.out.println("encoded plaint password "+passwordEncoder.encode(password));
//
//            System.out.println("password encoder to check is is matches or not "+passwordEncoder.matches(password,employeeRegisterDTO.getPassword()));


            if (passwordEncoder.matches(password,employeeRegisterDTO.getPassword())) {

                return Optional.of(employeeRegisterDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateEmpPassword(EmployeeRegisterDTO employeeRegisterDTO) {
        System.out.println("Service update by password "+employeeRegisterDTO);
        sendEmailEmp(employeeRegisterDTO);
        employeeRegisterDTO.setPassword(passwordEncoder.encode(employeeRegisterDTO.getPassword()));
        deptRepo.updateEmpPassword(employeeRegisterDTO);
    }

    @Override
    public boolean processForgetEmpPassword(String emailAddress) {
        Optional<EmployeeRegisterDTO> employeeRegisterDTOOptional = deptRepo.findByEmpEmailAddress(emailAddress);
        if (employeeRegisterDTOOptional.isPresent()) {
            EmployeeRegisterDTO employeeRegisterDTO = employeeRegisterDTOOptional.get();
            String newPassword = deptRepo.generateRandomPassword();

            employeeRegisterDTO.setPassword(passwordEncoder.encode(newPassword)); // Update the password field directly
            employeeRegisterDTO.setCount(0);
            System.out.println("Running forgetPassword = " +newPassword);

            Optional<EmployeeRegisterDTO> employeeRegisterDTOOptional1 =  deptRepo.mergeEmp(employeeRegisterDTO); // Save the changes
            System.out.println(employeeRegisterDTOOptional1.get());
            System.out.println("Running forgetPassword = " +newPassword);

            sendEmailUpdateEmp(employeeRegisterDTO.getEmailAddress(), newPassword);
            return true;
        }
        return false;
    }

    @Override
    public boolean processForgetDeptAdminPassword(String emailAddress) {
        Optional<DeptAdminDTO> deptAdminDTOOptional = deptRepo.findByDeptAdminEmailAddress(emailAddress);
        if (deptAdminDTOOptional.isPresent()) {
            DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();
            String newPassword = deptRepo.generateRandomPassword();

            deptAdminDTO.setPassword(passwordEncoder.encode(newPassword)); // Update the password field directly
            deptAdminDTO.setFailedAttemptsCount(0);
            deptAdminDTO.setCount(0);
            deptAdminDTO.setFailedAttemptDateTime(null);
            deptAdminDTO.setAccountLocked(false);
            System.out.println("Running forgetPassword = " +newPassword);

            Optional<DeptAdminDTO> deptAdminDTOOptional1 =  deptRepo.mergeDeptAdmin(deptAdminDTO); // Save the changes
            System.out.println(deptAdminDTOOptional1.get());
            System.out.println("Running forgetPassword = " +newPassword);

            sendEmailUpdateEmp(deptAdminDTO.getEmailAddress(), newPassword);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void handleFailedDeptAdminLoginAttempt(DeptAdminDTO deptAdminDTO) {
        // Perform your logic to handle failed login attempt
        if (deptAdminDTO.getFailedAttemptDateTime() == null ||
                deptAdminDTO.getFailedAttemptDateTime().plusHours(1).isBefore(LocalDateTime.now())) {
            deptAdminDTO.setFailedAttemptsCount(1);
        } else {
            deptAdminDTO.setFailedAttemptsCount(deptAdminDTO.getFailedAttemptsCount() + 1);
        }

        deptAdminDTO.setFailedAttemptDateTime(LocalDateTime.now());
        if (deptAdminDTO.getFailedAttemptsCount() >= 3) {
            deptAdminDTO.setAccountLocked(true);
        }

        // Save the managed entity
        deptRepo.mergeDeptAdmin(deptAdminDTO);
    }

    @Override
    @Transactional
    public void lockDeptAdminAccount(DeptAdminDTO deptAdminDTO) {
        System.out.println("locking "+deptAdminDTO);
        deptAdminDTO.setAccountLocked(true);
//        DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();
        String newPassword = deptRepo.generateRandomPassword();
        deptAdminDTO.setPassword(passwordEncoder.encode(newPassword));
        sendEmailUpdateEmp(deptAdminDTO.getEmailAddress(), newPassword);
        System.out.println(newPassword);

        deptAdminDTO.setCount(0);
//        deptAdminDTO.setPassword(null);
        deptRepo.mergeDeptAdmin(deptAdminDTO);
    }

    private void sendEmailUpdateEmp(String emailAddress, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Your New Password");
//        message.setText("Your new password is: " + newPassword);
        message.setText("Your new password is: " + newPassword+"\n Now please login through this password and reset your password");
        emailSender.send(message);
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
    public List<RaiseComplaintDTO> findAllComplaintsForDeptAdmin(String deptAssign) {
        System.out.println("Running findAllComplaintsForAdmin in DeptServiceImpl");
        return deptRepo.findAllComplaintsForDeptAdmin(deptAssign);
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
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForEmp(String complaintType, String city, String complaintStatus) {
        System.out.println("1");
        return deptRepo.searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForEmp(complaintType, city,complaintStatus);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndComplaintStatusForEmp(String complaintType, String complaintStatus){
        System.out.println("2");
        return deptRepo.searchComplaintsBycomplaintTypeAndComplaintStatusForEmp(complaintType,complaintStatus);
    }
    @Override
    public List<RaiseComplaintDTO> searchComplaintsCityAndComplaintStatusForEmp(String city, String complaintStatus){
        System.out.println("3");
        return deptRepo.searchComplaintsCityAndComplaintStatusForEmp(city,complaintStatus);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeAndCityForEmp(String complaintType, String city){
        System.out.println("4");
        return deptRepo.searchComplaintsBycomplaintTypeAndCityForEmp(complaintType, city);
    }

    @Override
    public List<RaiseComplaintDTO> searchComplaintsBycomplaintTypeOrcityForEmpDept(String complaintType, String city, String complaintStatus) {
        System.out.println("Running searchComplaintsBycomplaintTypeOrcityForAdmin in DeptServiceImpl for emp");
        return deptRepo.searchComplaintsBycomplaintTypeOrcityForDeptEmp(complaintType,city,complaintStatus);
    }

    @Override
    public List<ResponseHistoryDTO> findComplaintHistoryByComplaintId(HistoryDTO historyDTO, WaterDeptDTO waterDeptDTO) {
        System.out.println("Fetching data for history: " + historyDTO);
        System.out.println("dept " + waterDeptDTO);

        List<HistoryDTO> historyList = complaintRepo.findComplaintHistoryByComplaintId(historyDTO);
        List<ResponseHistoryDTO> responseHistoryDTOS = new ArrayList<>();

        if (historyList != null && !historyList.isEmpty()) {
            String deptName = deptRepo.findByDeptName(waterDeptDTO.getDeptId()); // Assuming waterDeptDTO has getDepartmentId() method
            System.out.println("conversion started");

            for (HistoryDTO history : historyList) {
//                history.setDepartmentId(Integer.parseInt(deptName));


                responseHistoryDTOS.add(new ResponseHistoryDTO(history.getId(), history.getComplaintId(), deptName, history.getComplaintStatus()));
                // Assuming HistoryDTO has setDeptName(String deptName) method
            }
        }

        System.out.println("service fetching is done");

        return responseHistoryDTOS;
    }

    @Override
    public List<WaterDeptDTO> getAllDepartments() {
        return deptRepo.getAllDepartments();
}

    public String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }
}
