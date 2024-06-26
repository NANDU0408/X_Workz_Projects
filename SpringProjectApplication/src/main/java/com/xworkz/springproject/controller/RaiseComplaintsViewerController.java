package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.user.EditComplaintDTO;
import com.xworkz.springproject.dto.user.ImageDownloadDTO;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.dto.user.SignUpDTO;
import com.xworkz.springproject.model.service.ComplaintService;
import com.xworkz.springproject.model.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData","userComplaintData"})
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



    @GetMapping("/editPage")
    public String updateDetailsAndUpload(@RequestParam(required = false) Integer complaintId,@ModelAttribute("editComplaintDTO") RaiseComplaintDTO editComplaintDTO,
                                         Model model) {
        System.out.println("Update process is initiated using DTO: " + editComplaintDTO);

        try {
            // Fetch complaint details by complaintId from database using service
            Optional<RaiseComplaintDTO> complaintDTOOptional = complaintService.findById(complaintId);

            if (complaintDTOOptional.isPresent()) {
                RaiseComplaintDTO raiseComplaintDTO = complaintDTOOptional.get();



                // Store raiseComplaintDTO in model attribute for use in the view
                model.addAttribute("raiseComplaintDTO", raiseComplaintDTO);
                model.addAttribute("message", "Complaint details retrieved successfully.");

            } else {
                model.addAttribute("message", "Complaint not found for id: " + editComplaintDTO.getComplaintId());
            }

        } catch (Exception e) {
            model.addAttribute("message", "Failed to retrieve complaint details: " + e.getMessage());
            e.printStackTrace();
        }

        // Return the view where you want to display the form
        return "registration/EditComplaints.jsp";
    }

    @PostMapping("/updateDescription")
    public String updateDescription(@ModelAttribute("raiseComplaintDTO") RaiseComplaintDTO raiseComplaintDTO,
                                    Model model) {
        try {
            Optional<RaiseComplaintDTO> updatedDTO = complaintService.mergeDescription(raiseComplaintDTO);

            if (updatedDTO.isPresent()) {
                model.addAttribute("message", "Description updated successfully.");
            } else {
                model.addAttribute("message", "Failed to update description.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Failed to update description: " + e.getMessage());
            e.printStackTrace();
        }

        // Redirect back to the edit page or any other appropriate view
        return "registration/EditComplaints.jsp";
    }

    @GetMapping("/searchComplaints")
    public String searchComplaints(
            @RequestParam("search") String keyword,
            @RequestParam(value = "type", required = false) String type,
            Model model) {

        List<RaiseComplaintDTO> searchResults = null;

        if ("type".equals(type)) {
            searchResults = complaintService.searchComplaintsByType(keyword);
        } else if ("city".equals(type)) {
            searchResults = complaintService.searchComplaintsByCity(keyword);
        } else if ("updatedDate".equals(type)) {
            searchResults = complaintService.searchComplaintsByUpdatedDate(keyword);
        }

        model.addAttribute("complaintLists", searchResults);
        return "registration/ViewComplaints.jsp";
    }
}