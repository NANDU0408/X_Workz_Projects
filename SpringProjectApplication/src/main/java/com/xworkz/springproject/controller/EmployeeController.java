package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.dept.*;
import com.xworkz.springproject.dto.requestDto.HistoryDTO;
import com.xworkz.springproject.dto.requestDto.RequestToDeptAndStatusOfComplaintDto;
import com.xworkz.springproject.dto.responseDto.DeptViewComplaintForEachCompliantDto;
import com.xworkz.springproject.dto.responseDto.ResponseHistoryDTO;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import com.xworkz.springproject.model.service.ComplaintService;
import com.xworkz.springproject.model.service.DeptAdminService;
import com.xworkz.springproject.model.service.DeptService;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes({"userData","userImageData","complaintData","adminData","deptAdminData","deptUserData","deptAdminData","deptUserData"})

public class EmployeeController {

        @Autowired
        private DeptService deptService;

        @Autowired
        private ComplaintService complaintService;

        @Autowired
        private DeptAdminService deptAdminService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/deptEmpViewComplaints")
        public String viewComplaints(Model model, HttpSession session) {
            EmployeeRegisterDTO loggedInEmp = (EmployeeRegisterDTO) session.getAttribute("deptUserData");
            List<EmployeeRegisterDTO> deptDTOList = deptService.getDeptIdAndDeptNameForDept();

            System.out.println(deptDTOList);
            model.addAttribute("departmentEmpLists",deptDTOList);

            if (loggedInEmp == null) {
                model.addAttribute("errorMsg", "Admin is not logged in or session has expired");
                return "registration/SignIn.jsp?role=deptemployee"; // Redirect to the admin login page
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

            model.addAttribute("assignedEmpComplaints",deptViewComplaintForEachCompliantDtoList);
            model.addAttribute("deptAdminComplaintLists", filteredComplaintList);
            System.out.println("Running viewComplaints in Controller: " + filteredComplaintList);
            return "registration/EmpUserComplaintView.jsp";
        }

        @PostMapping("/assignedEmpComplaints")
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

        @GetMapping("/searchComplaintsDeptEmp")
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

        @PostMapping("/historyEmp")
        public String viewComplaintHistory(@RequestParam("complaintId") int complaintId, @RequestParam("departmentId") int departmentId, Model model) {
            // Fetch history based on complaintId
            log.info("history c=" +complaintId+" d="+departmentId);
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setComplaintId(complaintId);
            WaterDeptDTO waterDeptDT= new WaterDeptDTO();
            waterDeptDT.setDeptId(departmentId);
            List<ResponseHistoryDTO> history = deptService.findComplaintHistoryByComplaintId(historyDTO,waterDeptDT);
            log.info("history" +history);
            // Add history to the model
            model.addAttribute("historyTable", history);

            // Return the view name to display the history
            return "registration/ComplaintHistory.jsp";
        }
}

