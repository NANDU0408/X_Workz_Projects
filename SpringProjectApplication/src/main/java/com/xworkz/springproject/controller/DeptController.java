package com.xworkz.springproject.controller;


import com.xworkz.springproject.dto.dept.WaterDeptDTO;
import com.xworkz.springproject.model.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData"})
public class DeptController {

    @Autowired
    private ComplaintService complaintService;


    @GetMapping("/adminReviewComplaints")
    public String adminViewComplaints(Model model) {
        List<WaterDeptDTO> departments = complaintService.getDeptIdAndDeptName(); // Example method to fetch departments from your service
        System.out.println("Running adminViewComplaints in DeptController" +departments);
        model.addAttribute("departments", departments);

        return "registration/AdminUserComplaints.jsp";
    }
}
