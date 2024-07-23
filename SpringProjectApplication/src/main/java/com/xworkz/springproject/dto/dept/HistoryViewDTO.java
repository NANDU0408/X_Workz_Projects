package com.xworkz.springproject.dto.dept;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryViewDTO {
    private int complaintId;
    private String deptName;  // Add this field

}
