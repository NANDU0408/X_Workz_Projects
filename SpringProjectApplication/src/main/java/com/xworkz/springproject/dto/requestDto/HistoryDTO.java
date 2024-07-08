package com.xworkz.springproject.dto.requestDto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name ="complaintHistory_form")
public class HistoryDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Complaint id should not be null")
    @Column(name = "complaint_id")
    private int complaintId;

    @NotNull(message = "Department id should not be null")
    @Column(name = "department_id")
    private int departmentId;

//    @NotNull(message = "Complaint status should not be null")
    @Column(name = "complaint_status")
    private String complaintStatus;
}
