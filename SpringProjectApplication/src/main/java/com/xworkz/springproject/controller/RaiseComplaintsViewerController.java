package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.ComplaintService;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData"})
public class RaiseComplaintsViewerController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;


    @PostMapping("/complaint")
    public String submitComplaint(@Valid @ModelAttribute("dto") RaiseComplaintDTO raiseComplaintDTO,
                                  BindingResult bindingResult, Model model, HttpSession session) {

        System.out.println("Running RaiseComplaintsViewerController" + raiseComplaintDTO);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/ViewComplaints.jsp";
        }

        // Fetch logged-in user details from session
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");
        if (loggedInUser == null) {
            model.addAttribute("failureMessage", "User is not logged in.");
            return "registration/ViewComplaints.jsp"; // Redirect to login page if user is not logged in
        }

        // Save the complaint using service
        System.out.println("Controller" + raiseComplaintDTO);
        Optional<RaiseComplaintDTO> savedDto = complaintService.saveComplaint(raiseComplaintDTO, loggedInUser);

        if (savedDto.isPresent()) {
            System.out.println("Controller Raise" + raiseComplaintDTO);
            model.addAttribute("successMessage", "Complaint submitted successfully!");
        } else {
            model.addAttribute("failureMessage", "Failed to submit complaint. Please try again.");
        }

        // Fetch all complaints related to the logged-in user
        List<RaiseComplaintDTO> userComplaints = complaintService.findAllComplaintsByUserId(loggedInUser.getId());
        model.addAttribute("complaintLists", userComplaints); // Add user's complaints to the model

        return "registration/ViewComplaints.jsp";
    }
}