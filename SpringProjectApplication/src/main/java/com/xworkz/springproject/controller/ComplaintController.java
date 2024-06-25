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
import java.util.Optional;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData"})
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;

    @PostMapping("submitComplaint")
    public String submitComplaint(@Valid @ModelAttribute("dto") RaiseComplaintDTO raiseComplaintDTO,
                                  BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration/RaiseComplaint.jsp";
        }

        // Fetch logged-in user details from session
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");

        if (loggedInUser == null) {
            model.addAttribute("failureMessage", "User not logged in. Please log in to submit a complaint.");
            return "registration/RaiseComplaint.jsp";
        }


        // Save the complaint using service
        Optional<RaiseComplaintDTO> savedDto = complaintService.saveComplaint(raiseComplaintDTO, loggedInUser);

        if (savedDto.isPresent()) {
            model.addAttribute("successMessage", "Complaint submitted successfully, please check your email for the complaint info!");
            complaintService.sendEmail(loggedInUser, savedDto.get());
        } else {
            model.addAttribute("failureMessage", "Failed to submit complaint. Please try again.");
        }

        // Add any additional model attributes needed for the view
        model.addAttribute("dto", raiseComplaintDTO); // Ensure the form is populated correctly on reload
        return "registration/RaiseComplaint.jsp";
    }

}
