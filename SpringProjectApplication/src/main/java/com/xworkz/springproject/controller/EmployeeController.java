package com.xworkz.springproject.controller;

import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.dept.WaterDeptDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
                model.addAttribute("errorMsg", "User is not logged in or session has expired");
                return "registration/SignIn.jsp?role=deptemployee"; // Redirect to the user login page
            }

            List<RaiseComplaintDTO> complaintList = deptService.findAllComplaintsForDeptAdmin(String.valueOf(loggedInEmp.getDept_id()));
            // Filter complaints where Department ID is not null
            List<RaiseComplaintDTO> filteredComplaintList = complaintList.stream()
                    .filter(complaint -> complaint.getAssignEmployee() != null)
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
    public ResponseEntity<String> updatedComplaint(@RequestBody RequestToDeptAndStatusOfComplaintDto requestToDeptAndStatusOfComplaintDto,
                                                   BindingResult bindingResult, HttpSession session) {
        System.out.println(" --- admin assigning department process is initiated : " + requestToDeptAndStatusOfComplaintDto);

        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException("Argument should not empty");
        }

        try {
            deptAdminService.assignDeptAndStatusForDeptEmp(requestToDeptAndStatusOfComplaintDto);
            System.out.println(requestToDeptAndStatusOfComplaintDto);

            HistoryDTO historyDTO = new HistoryDTO();
            RaiseComplaintDTO raiseComplaintDTO = new RaiseComplaintDTO();
            // Ensure to populate historyDTO and raiseComplaintDTO with appropriate values

            Optional<RaiseComplaintDTO> updatedComplaint = deptService.saveHistoryForDept(historyDTO, raiseComplaintDTO, requestToDeptAndStatusOfComplaintDto);
            if (updatedComplaint.isPresent()) {
                System.out.println("History saved successfully: " + updatedComplaint.get());
                return ResponseEntity.ok("Complaint status updated successfully.");
            } else {
                System.out.println("Failed to save history.");
                return ResponseEntity.status(500).body("Failed to save history.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
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
                complaints = deptService.searchComplaintsBycomplaintTypeAndCityAndComplaintStatusForEmp(complaintType, city,complaintStatus);
                System.out.println(complaints);
            } else if (complaintType != null && !complaintType.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
                complaints = deptService.searchComplaintsBycomplaintTypeAndComplaintStatusForEmp(complaintType,complaintStatus);
                System.out.println(complaints);
            } else if (city != null &&!city.isEmpty() && complaintStatus != null && !complaintStatus.isEmpty()) {
                complaints = deptService.searchComplaintsCityAndComplaintStatusForEmp(city,complaintStatus);
                System.out.println(complaints);
            } else if (complaintType != null && !complaintType.isEmpty() && city != null && !city.isEmpty()) {
                complaints = deptService.searchComplaintsBycomplaintTypeAndCityForEmp(complaintType,city);
                System.out.println(complaints);
            } else if (complaintType != null && !complaintType.isEmpty() || city != null && !city.isEmpty() || complaintStatus != null && !complaintStatus.isEmpty()) {
                // Search by type or city
                complaints = deptService.searchComplaintsBycomplaintTypeOrcityForEmpDept(complaintType,city,complaintStatus);
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




            model.addAttribute("assignedEmpComplaints", deptViewComplaintForEachCompliantDtoList);
            return "registration/EmpUserComplaintView.jsp";
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

