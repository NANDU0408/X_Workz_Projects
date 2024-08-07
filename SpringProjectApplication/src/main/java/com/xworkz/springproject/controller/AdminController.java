package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.admin.AdminSignInDTO;
import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.DeptAdminResetPasswordDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
@SessionAttributes({"userData","userImageData","complaintData","adminData"})
public class AdminController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private DeptAdminService deptAdminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AdminController(){
        log.info("Created Admin Controller");
    }

    @PostMapping("/admin")
    public String login(@Valid @ModelAttribute("loginForm") AdminSignInDTO adminSignInDTO, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "registration/SignIn.jsp?role=admin";
        }

        Optional<AdminDTO> adminDTOOptional = signUpService.validateAdminSignIn(adminSignInDTO.getEmailAddress(), adminSignInDTO.getPassword());
        if (adminDTOOptional.isPresent()) {
            // Set adminData in the session
            AdminDTO loggedInAdmin = adminDTOOptional.get();
            session.setAttribute("adminData", loggedInAdmin);
            model.addAttribute("adminData", loggedInAdmin);
            return "registration/AdminHome.jsp"; // Redirect to admin home page
        } else {
            model.addAttribute("errorMsg", "Invalid email or password");
            return "registration/SignIn.jsp?role=admin"; // Redirect back to admin login page with error message
        }
    }

    @GetMapping("/adminHome")
    public String getAdminHome(Model model) {
        return "registration/AdminHome.jsp"; // Return the logical view name
    }

    @GetMapping("/userDetails")
    public String getUserDetails(Model model, HttpSession session) {
        AdminDTO loggedInAdmin = (AdminDTO) session.getAttribute("adminData");
        if (loggedInAdmin == null) {
            model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
            return "registration/SignIn.jsp?role=admin"; // Redirect to the admin login page
        }

        // Fetch SignUpDTO details from the database
        List<SignUpDTO> signUpDetails = signUpService.getAllSignUpDetails();
        // Add signUpDetails to the model attribute
        model.addAttribute("detailsList", signUpDetails);
        return "registration/AdminUserView.jsp"; // Return the logical view name
    }

    @GetMapping("/adminViewComplaints")
    public String viewComplaints(Model model, HttpSession session) {
        AdminDTO loggedInAdmin = (AdminDTO) session.getAttribute("adminData");
        List<WaterDeptDTO> deptDTOList = complaintService.getDeptIdAndDeptName();

        System.out.println(deptDTOList);
        model.addAttribute("departments",deptDTOList);

        if (loggedInAdmin == null) {
            model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
            return "registration/SignIn.jsp?role=admin"; // Redirect to the admin login page
        }

        List<RaiseComplaintDTO> complaintList = complaintService.findAllComplaintsForAdmin();
        model.addAttribute("adminComplaintLists", complaintList);
        System.out.println("Running viewComplaints in Controller: " + complaintList);
        return "registration/AdminUserComplaints.jsp";
    }

    @GetMapping("/adminViewActiveComplaints")
    public String viewActiveComplaints(RaiseComplaintDTO raiseComplaintDTO,Model model, HttpSession session) {
        AdminDTO loggedInAdmin = (AdminDTO) session.getAttribute("adminData");
        if (loggedInAdmin == null) {
            model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
            return "registration/SignIn.jsp?role=admin"; // Redirect to the admin login page
        }

        List<RaiseComplaintDTO> complaintList = complaintService.findComplaintsByStatusForAdmin("Active");
        model.addAttribute("adminComplaintLists", complaintList);
        System.out.println("Running viewActiveComplaints in Controller: " + complaintList);
        return "registration/AdminUserComplaints.jsp";
    }

    @GetMapping("/adminViewInactiveComplaints")
    public String viewInactiveComplaints(RaiseComplaintDTO raiseComplaintDTO,Model model, HttpSession session) {
        AdminDTO loggedInAdmin = (AdminDTO) session.getAttribute("adminData");
        if (loggedInAdmin == null) {
            model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
            return "registration/SignIn.jsp?role=admin"; // Redirect to the admin login page
        }

        List<RaiseComplaintDTO> complaintList = complaintService.findComplaintsByStatusForAdmin("Inactive");
        model.addAttribute("adminComplaintLists", complaintList);
        System.out.println("Running viewInactiveComplaints in Controller: " + complaintList);
        return "registration/AdminUserComplaints.jsp";
    }


    @GetMapping("/searchComplaintsAdmin")
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
            complaints = complaintService.searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForAdmin(complaintType, city,complaintStatus);
        } else if (complaintType != null && !complaintType.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
            complaints = complaintService.searchComplaintsBycomplaintTypeAndComplaintStatusForAdmin(complaintType,complaintStatus);
        } else if (city != null &&!city.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
            complaints = complaintService.searchComplaintsCityAndComplaintStatusForAdmin(city,complaintStatus);
        }else if (complaintType != null && !complaintType.isEmpty() && city != null && !city.isEmpty()) {
            complaints = complaintService.searchComplaintsBycomplaintTypeAndCityForAdmin(complaintType,city);
        }  else if (complaintType != null && !complaintType.isEmpty() || city != null && !city.isEmpty() || complaintStatus != null && !complaintStatus.isEmpty()) {
            // Search by type or city
            complaints = complaintService.searchComplaintsBycomplaintTypeOrcityForAdmin(complaintType,city,complaintStatus);
            System.out.println("Running searchComplaints in AdminController for any of complaint type or city" +complaints);
        } else {
            // Fetch all complaints
            System.out.println("default search");
            complaints = complaintService.findAllComplaints();
        }

        model.addAttribute("adminComplaintLists", complaints);
        return "registration/AdminUserComplaints.jsp";
    }

    @PostMapping("assignToDept")
    public String updatedComplaint(RaiseComplaintDTO raiseComplaintDTO, HistoryDTO historyDTO,RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto,
                                   Model model, HttpSession session){
        System.out.println("admin assigning department process is initiated : "+requestToDeptAndStatusOfComplaintDto);

        adminService.assignDeptAndStatus(requestToDeptAndStatusOfComplaintDto);
        System.out.println(requestToDeptAndStatusOfComplaintDto);

        Optional<RaiseComplaintDTO> updatedComplaint = complaintService.saveHistory(historyDTO, raiseComplaintDTO, requestToDeptAndStatusOfComplaintDto);
        if (updatedComplaint.isPresent()) {
            System.out.println("History saved successfully: " + updatedComplaint.get());
        } else {
            System.out.println("Failed to save history.");
        }

        return viewComplaints(model,session);
    }

    @GetMapping("/getDeptENames")
    public String showAddEmployeeForm(Model model) {
        List<WaterDeptDTO> departments = deptService.getAllDepartments();

        model.addAttribute("addAdminDepartments", departments);// Initialize form backing object
        return "registration/AddDeptAdmin.jsp"; // Return the for view
    }

    @PostMapping("/deptAdminReg")
    public String empSignUp(@Valid @ModelAttribute("dto") DeptAdminDTO deptAdminDTO, BindingResult bindingResult, Model model) {

        List<WaterDeptDTO> departments = deptService.getAllDepartments();
        model.addAttribute("addDepartments", departments);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/AddDeptAdmin.jsp";
        }

        if (deptAdminService.checkDeptAdminEmailExists(deptAdminDTO.getEmailAddress())) {
            model.addAttribute("failureMessage", "Email already exists. Please try a different one.");
            return "registration/AddDeptAdmin.jsp";
        }

        if (deptAdminService.checkDeptAdminPhoneNumberExists(deptAdminDTO.getMobileNumber())) {
            model.addAttribute("failureMessage", "Phone number already exists. Please try a different one.");
            return "registration/AddDeptAdmin.jsp";
        }

        Optional<DeptAdminDTO> savedDeptAdminDTO = deptAdminService.saveDeptAdmin(deptAdminDTO);
        if (savedDeptAdminDTO.isPresent()) {
            model.addAttribute("successMessage", "Sign-Up successful! Your password is sent to your email address: " + savedDeptAdminDTO.get().getEmailAddress());
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }

        return "registration/AddDeptAdmin.jsp";
    }

    @PostMapping("/resetDeptAdminPassword")
    public String resetEmpPassword(@ModelAttribute("resetAdminPasswordForm") DeptAdminResetPasswordDTO deptAdminResetPasswordDTO, Model model) {
        System.out.println(deptAdminResetPasswordDTO);

        if (!deptAdminResetPasswordDTO.getNewPassword().equals(deptAdminResetPasswordDTO.getConfirmNewPassword())) {
            model.addAttribute("errorMessage", "New Password and Confirm Password do not match.");
            return "registration/DeptAdminResetPassword.jsp";
        }

        Optional<DeptAdminDTO> deptAdminDTOOptional = deptAdminService.findByDeptAdminEmailAddress(deptAdminResetPasswordDTO.getEmailAddress());
        if (deptAdminDTOOptional.isPresent()) {
            DeptAdminDTO deptAdminDTO = deptAdminDTOOptional.get();

            if (!passwordEncoder.matches(deptAdminResetPasswordDTO.getPassword(), deptAdminDTO.getPassword())) {
                model.addAttribute("errorMessage", "Current password is incorrect.");
                return "registration/DeptAdminResetPassword.jsp";
            }else {
                deptAdminDTO.setPassword(deptAdminResetPasswordDTO.getNewPassword());
                deptAdminService.updateDeptAdminPassword(deptAdminDTO); // Use updatePassword method to save the updated password
                System.out.println("Plain password " + deptAdminResetPasswordDTO);
                System.out.println("New Password " + deptAdminDTO);

                model.addAttribute("successMessage", "Password reset successful. Please log in with your new password.");
                model.addAttribute("reset", true);
                return "registration/SignIn.jsp?role=deptadmin";
            }
        } else {
            model.addAttribute("errorMessage", "User not found. Please try again.");
            return "registration/DeptAdminResetPassword.jsp";
        }
    }



}