package com.xworkz.springproject.dto.requestDto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestToDeptAndStatusOfComplaintDto {

    @NotNull(message = "Complaint id should not be null")
    private int complaintId;

//    @NotNull(message = "Department id should not be null")
    private int departmentId;

    @NotNull(message = "complaint status should not be null")
    private String complaintStatus;

//    @NotNull(message = "emp Id should not be null")
    private String empId;
}
