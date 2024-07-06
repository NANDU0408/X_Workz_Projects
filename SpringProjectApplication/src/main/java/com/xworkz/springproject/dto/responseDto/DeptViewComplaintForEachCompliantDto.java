package com.xworkz.springproject.dto.responseDto;


import com.xworkz.springproject.dto.dept.EmployeeRegisterDTO;
import com.xworkz.springproject.dto.user.RaiseComplaintDTO;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeptViewComplaintForEachCompliantDto {
   private RaiseComplaintDTO raiseComplaintDTO;
   private List<EmployeeRegisterDTO> employeeRegisterDTOList;
}
