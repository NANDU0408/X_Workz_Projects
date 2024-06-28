package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.admin.AdminDTO;
import com.xworkz.springproject.dto.admin.AdminSignInDTO;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.ComplaintService;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
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
@SessionAttributes({"userData","userImageData","complaintData","adminData"})
public class AdminController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private ComplaintService complaintService;

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
    public String viewComplaints(RaiseComplaintDTO raiseComplaintDTO,Model model, HttpSession session) {
        AdminDTO loggedInAdmin = (AdminDTO) session.getAttribute("adminData");
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
            Model model
    ) {
        List<RaiseComplaintDTO> complaints;

        System.out.println("Type: " + complaintType + ", City: " + city);

        if (complaintType != null && !complaintType.isEmpty() && city != null && !city.isEmpty()) {
            // Search by both type and city
            complaints = complaintService.searchComplaintsByComplaintTypeAndCityForAdmin(complaintType, city);
        } else if (complaintType != null && !complaintType.isEmpty() || city != null && !city.isEmpty()) {
            // Search by type or city
            complaints = complaintService.searchComplaintsBycomplaintTypeOrcityForAdmin(complaintType,city);
        } else {
            // Fetch all complaints
            complaints = complaintService.findAllComplaints();
        }

        model.addAttribute("adminComplaintLists", complaints);
        return "registration/AdminUserComplaints.jsp";
    }
}