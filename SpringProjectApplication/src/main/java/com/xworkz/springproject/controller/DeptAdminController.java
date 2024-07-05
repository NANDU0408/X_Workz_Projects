package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.admin.AdminSignInDTO;
import com.xworkz.springproject.dto.dept.DeptAdminDTO;
import com.xworkz.springproject.dto.dept.DeptAdminSignInDTO;
import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.DeptService;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData","deptAdminData"})
public class DeptAdminController {

    @Autowired
    private DeptService deptService;

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
    public String signUp(@Valid @ModelAttribute("dto") EmployeeRegisterDTO employeeRegisterDTO, BindingResult bindingResult, Model model) {
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

        Optional<EmployeeRegisterDTO> savedEmpDto = deptService.saveEmp(employeeRegisterDTO);
        if (savedEmpDto.isPresent()) {
            model.addAttribute("successMessage", "Sign-Up successful! Your password is sent to your email address: " + savedEmpDto.get().getEmailAddress());
            deptService.sendEmailEmp(savedEmpDto.get());
        } else {
            model.addAttribute("failureMessage", "Sign-Up failed. Please try again.");
        }
        return "registration/AddEmployee.jsp";
    }
}
