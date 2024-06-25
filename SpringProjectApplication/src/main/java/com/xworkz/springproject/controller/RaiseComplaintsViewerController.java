package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.ComplaintService;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData"})
public class RaiseComplaintsViewerController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private SignUpService signUpService;



    @GetMapping("/editComplaint/{complaintId}")
    public String editComplaint(@PathVariable("complaintId") int complaintId, Model model, HttpSession session) {
        Optional<RaiseComplaintDTO> complaintOpt = complaintService.findComplaintById(complaintId);
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");
        if (complaintOpt.isPresent()) {
            model.addAttribute("complaint", complaintOpt.get());
            System.out.println("Running editComplaint in Controller "+complaintOpt);

            RaiseComplaintDTO raiseComplaintDTO = complaintOpt.get();
            Optional<RaiseComplaintDTO> savedDto = complaintService.saveComplaint(raiseComplaintDTO, loggedInUser);
            return "registration/ViewComplaints.jsp"; // JSP page for editing the complaint
        } else {
            model.addAttribute("error", "Complaint not found");
            return "registration/ViewComplaints.jsp"; // Error page
        }
    }

    @GetMapping("/viewComplaints")
    public String viewComplaints(Model model,HttpSession session) {
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");
        List<RaiseComplaintDTO> complaintList = complaintService.findAllComplaints(loggedInUser.getId());
        model.addAttribute("complaintLists", complaintList);
        System.out.println("Running viewComplaints in Controller"+complaintList);
        return "registration/ViewComplaints.jsp";
    }

    @GetMapping("/viewActiveComplaints")
    public String viewActiveComplaints(Model model, HttpSession session) {
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");
        List<RaiseComplaintDTO> complaintList = complaintService.findComplaintsByStatus(loggedInUser.getId(), "Active"); // Assuming you have a method to fetch active complaints
        model.addAttribute("complaintLists", complaintList);
        System.out.println("Running viewActiveComplaints in Controller" + complaintList);
        return "registration/ViewComplaints.jsp";
    }

    @GetMapping("/viewInactiveComplaints")
    public String viewInactiveComplaints(Model model, HttpSession session) {
        SignUpDTO loggedInUser = (SignUpDTO) session.getAttribute("userData");
        List<RaiseComplaintDTO> complaintList = complaintService.findComplaintsByStatus(loggedInUser.getId(), "Inactive"); // Assuming you have a method to fetch inactive complaints
        model.addAttribute("complaintLists", complaintList);
        System.out.println("Running viewInactiveComplaints in Controller" + complaintList);
        return "registration/ViewComplaints.jsp";
    }
    }