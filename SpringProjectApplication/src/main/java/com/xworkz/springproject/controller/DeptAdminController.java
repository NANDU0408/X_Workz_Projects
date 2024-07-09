package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.admin.AdminSignInDTO;
import com.xworkz.springproject.dto.dept.*;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.responseDto.DeptViewComplaintForEachCompliantDto;
import com.xworkz.springproject.dto.user.*;
import com.xworkz.springproject.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData","deptAdminData","deptUserData","deptAdminData"})
public class DeptAdminController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private DeptAdminService deptAdminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/deptAdmin")
    public String login(@Valid @ModelAttribute("loginForm") DeptAdminSignInDTO deptAdminSignInDTO, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp?role=deptadmin";
        }

        Optional<DeptAdminDTO> deptAdminDTOOptional = deptService.validateDeptAdminSignIn(deptAdminSignInDTO.getEmailAddress(), deptAdminSignInDTO.getPassword());
        if (deptAdminDTOOptional.isPresent()) {
            // Set adminData in the session
            DeptAdminDTO loggedInAdmin = deptAdminDTOOptional.get();
            session.setAttribute("deptAdminData", loggedInAdmin);
            model.addAttribute("deptAdminData", loggedInAdmin);
            return "registration/DeptAdminHome.jsp"; // Redirect to admin home page
        } else {
            model.addAttribute("errorMsg", "Invalid email or password");
            return "registration/SignIn.jsp?role=deptadmin"; // Redirect back to admin login page with error message
        }
    }



    @PostMapping("empReg")
    public String empSignUp(@Valid @ModelAttribute("dto") EmployeeRegisterDTO employeeRegisterDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/AddEmployee.jsp";
        }

        if (deptService.checkEmailExists(employeeRegisterDTO.getEmailAddress())) {
            model.addAttribute("failureMessage", "Email already exists. Please try a different one.");
            return "registration/AddEmployee.jsp";
        }

        if (deptService.checkPhoneNumberExists(employeeRegisterDTO.getMobileNumber())) {
            model.addAttribute("failureMessage", "Phone number already exists. Please try a different one.");
            return "registration/AddEmployee.jsp";
        }


        // Encrypt the password before saving
//        String encodedPassword = passwordEncoder.encode(employeeRegisterDTO.getPassword());
//        employeeRegisterDTO.setPassword(encodedPassword);

        Optional<EmployeeRegisterDTO> savedEmpDto = deptService.saveEmp(employeeRegisterDTO);
        if (savedEmpDto.isPresent()) {
            model.addAttribute("successMessage", "Sign-Up successful! Your password is sent to your email address: " + savedEmpDto.get().getEmailAddress());
//            deptService.sendEmailEmp(savedEmpDto.get());
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }
        return "registration/AddEmployee.jsp";
    }

    @PostMapping("/deptEmployeeLogin")
    public String login(@Valid @ModelAttribute("loginForm") DeptEmployeeSignInDTO deptEmployeeSignInDTO, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp?role=deptemployee";
        }

        Optional<EmployeeRegisterDTO> employeeRegisterDTOOptional = deptService.findByEmpEmailAddress(deptEmployeeSignInDTO.getEmailAddress());
        if (employeeRegisterDTOOptional.isPresent()){
            EmployeeRegisterDTO employeeRegisterDTO = employeeRegisterDTOOptional.get();
            if (employeeRegisterDTO.getCount() == 0){
                return "registration/EmployeeResetPassword.jsp";
            }else {

                Optional<EmployeeRegisterDTO> registerDTOOptional = deptService.validateSignInEmp(deptEmployeeSignInDTO.getEmailAddress(), deptEmployeeSignInDTO.getPassword());
                if (registerDTOOptional.isPresent()) {
                    // Set adminData in the session
                    EmployeeRegisterDTO loggedInEmp = registerDTOOptional.get();
                    session.setAttribute("deptUserData", loggedInEmp);
                    model.addAttribute("deptUserData", loggedInEmp);
                    return "registration/DeptEmpHome.jsp"; // Redirect to admin home page
                } else {
                    model.addAttribute("errorMsg", "Invalid email or password");
                    return "registration/SignIn.jsp?role=deptemployee"; // Redirect back to admin login page with error message
                }
            }

        }else {
            model.addAttribute("errorMsg", "Invalid email or password");
            return "registration/SignIn.jsp?role=deptemployee";
        }
    }

    @PostMapping("/resetEmpPassword")
    public String resetEmpPassword(@ModelAttribute("resetEmpPasswordForm") EmployeeResetPasswordDTO employeeResetPasswordDTO, Model model) {
        if (!employeeResetPasswordDTO.getNewPassword().equals(employeeResetPasswordDTO.getConfirmNewPassword())) {
            model.addAttribute("errorMessage", "New Password and Confirm Password do not match.");
            return "registration/EmployeeResetPassword.jsp";
        }

        Optional<EmployeeRegisterDTO> employeeRegisterDTOOptional = deptService.findByEmpEmailAddress(employeeResetPasswordDTO.getEmailAddress());
        if (employeeRegisterDTOOptional.isPresent()) {
            EmployeeRegisterDTO employeeRegisterDTO = employeeRegisterDTOOptional.get();
            employeeRegisterDTO.setPassword(employeeResetPasswordDTO.getNewPassword());
            deptService.updateEmpPassword(employeeRegisterDTO); // Use updatePassword method to save the updated password

            model.addAttribute("successMessage", "Password reset successful. Please log in with your new password.");
            model.addAttribute("reset", true);
            return "registration/SignIn.jsp?role=deptemployee";
        } else {
            model.addAttribute("errorMessage", "User not found. Please try again.");
            return "registration/EmployeeResetPassword.jsp";
        }
    }

    @GetMapping("/deptAdminViewComplaints")
    public String viewComplaints(Model model, HttpSession session) {
        DeptAdminDTO loggedInAdmin = (DeptAdminDTO) session.getAttribute("deptAdminData");
        List<EmployeeRegisterDTO> deptDTOList = deptService.getDeptIdAndDeptNameForDept();

        System.out.println(deptDTOList);
        model.addAttribute("departmentLists",deptDTOList);

        if (loggedInAdmin == null) {
            model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
            return "registration/SignIn.jsp?role=deptadmin"; // Redirect to the admin login page
        }

        List<RaiseComplaintDTO> complaintList = deptService.findAllComplaintsForDeptAdmin();
        // Filter complaints where Department ID is not null
        List<RaiseComplaintDTO> filteredComplaintList = complaintList.stream()
                .filter(complaint -> complaint.getDeptAssign() != null)
                .collect(Collectors.toList());

        List<DeptViewComplaintForEachCompliantDto> deptViewComplaintForEachCompliantDtoList = new ArrayList<>();
        filteredComplaintList.stream().forEach((employee)->{
            Optional<List<EmployeeRegisterDTO>> employeeRegisterDTOList = deptService.findEmpoloyeeByDeptId(Integer.parseInt(employee.getDeptAssign()));
            deptViewComplaintForEachCompliantDtoList.add(new DeptViewComplaintForEachCompliantDto(employee,employeeRegisterDTOList.get()));
        });

        model.addAttribute("assignedComplaints",deptViewComplaintForEachCompliantDtoList);
        model.addAttribute("deptAdminComplaintLists", filteredComplaintList);
        System.out.println("Running viewComplaints in Controller: " + filteredComplaintList);
        return "registration/DeptUserComplaints.jsp";
    }

    @PostMapping("assignToDeptEmp")
    public String updatedComplaint(RaiseComplaintDTO raiseComplaintDTO, HistoryDTO historyDTO, RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto,
                                   Model model, HttpSession session){
        System.out.println("admin assigning department process is initiated : "+requestToDeptAndStatusOfComplaintDto);

        deptAdminService.assignDeptAndStatusForDeptAdmin(requestToDeptAndStatusOfComplaintDto);
        System.out.println(requestToDeptAndStatusOfComplaintDto);

        requestToDeptAndStatusOfComplaintDto.setDepartmentId(Integer.parseInt(raiseComplaintDTO.getDeptAssign()));

        Optional<RaiseComplaintDTO> updatedComplaint = deptService.saveHistoryForDept(historyDTO, raiseComplaintDTO, requestToDeptAndStatusOfComplaintDto);
        if (updatedComplaint.isPresent()) {
            System.out.println("History saved successfully: " + updatedComplaint.get());
        } else {
            System.out.println("Failed to save history.");
        }

        return viewComplaints(model,session);
    }

    @GetMapping("/searchComplaintsDeptAdmin")
    public String searchComplaints(
            @RequestParam(name = "type",required = false) String complaintType,
            @RequestParam(name = "area", required = false) String city,
            @RequestParam(name = "status", required = false) String complaintStatus,
            Model model
    ) {
        List<RaiseComplaintDTO> complaints;

        System.out.println("Type: " + complaintType + ", City: " + city);

        if (complaintType != null && !complaintType.isEmpty() && city != null && !city.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
            // Search by both type and city
            System.out.println("check for both type and city");
            complaints = deptService.searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(complaintType, city,complaintStatus);
        } else if (complaintType != null && !complaintType.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
            complaints = deptService.searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(complaintType,complaintStatus);
        } else if (city != null &&!city.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
            complaints = deptService.searchComplaintsCityAndComplaintStatusForAdmin(city,complaintStatus);
        } else if (complaintType != null && !complaintType.isEmpty() && city != null && !city.isEmpty()) {
            complaints = deptService.searchComplaintsBycomplaintTypeAndCityForAdmin(complaintType,city);
        } else if (complaintType != null && !complaintType.isEmpty() || city != null && !city.isEmpty() || complaintStatus != null && !complaintStatus.isEmpty()) {
            // Search by type or city
            complaints = deptService.searchComplaintsBycomplaintTypeOrcityForAdminDept(complaintType,city,complaintStatus);
            System.out.println("Running searchComplaints in AdminController for any of complaint type or city" +complaints);
        } else {
            // Fetch all complaints
            System.out.println("default search");
            complaints = complaintService.findAllComplaints();
        }

//        List<RaiseComplaintDTO> complaintList = deptService.findAllComplaintsForDeptAdmin();
        // Filter complaints where Department ID is not null
        List<RaiseComplaintDTO> filteredComplaintList = complaints.stream()
                .filter(complaint -> complaint.getDeptAssign() != null)
                .collect(Collectors.toList());

        List<DeptViewComplaintForEachCompliantDto> deptViewComplaintForEachCompliantDtoList = new ArrayList<>();
        filteredComplaintList.stream().forEach((employee)->{
            Optional<List<EmployeeRegisterDTO>> employeeRegisterDTOList = deptService.findEmpoloyeeByDeptId(Integer.parseInt(employee.getDeptAssign()));
            deptViewComplaintForEachCompliantDtoList.add(new DeptViewComplaintForEachCompliantDto(employee,employeeRegisterDTOList.get()));
        });


        model.addAttribute("assignedComplaints", deptViewComplaintForEachCompliantDtoList);
        return "registration/DeptUserComplaints.jsp";
    }

    @PostMapping("/history")
    public String viewComplaintHistory(@RequestParam("complaintId") int complaintId, Model model) {
        // Fetch history based on complaintId
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setComplaintId(complaintId);
        List<HistoryDTO> history = deptService.findCompaintHistoryByComplaintId(historyDTO);

        // Add history to the model
        model.addAttribute("historyTable", history);

        // Return the view name to display the history
        return "registration/ComplaintHistory.jsp";
    }
}
