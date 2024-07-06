package com.xworkz.springproject.dto.user;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name ="raiseComplaint_form")
public class RaiseComplaintDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int complaintId;

    @NotNull(message = "Complaint Type should not be null")
    @Size(min = 3, max = 50, message = "Complaint Type must be between 3 and 50 characters")
    @Column(name = "complaint_type")
    private String complaintType;

    @NotNull(message = "Country should not be null")
    @Size(min = 3, max = 50, message = "Country must be between 3 and 50 characters")
    @Column(name = "country")
    private String country;

    @NotNull(message = "State should not be null")
    @Size(min = 3, max = 50, message = "State must be between 3 and 50 characters")
    @Column(name = "state")
    private String state;

    @NotNull(message = "City should not be null")
    @Size(min = 3, max = 50, message = "City must be between 3 and 50 characters")
    @Column(name = "city")
    private String city;

    @NotNull(message = "Address should not be null")
    @Size(min = 5, max = 300, message = "Address must be between 5 and 300 characters")
    @Column(name = "address")
    private String address;

    @NotNull(message = "Description should not be null")
    @Size(min = 5, max = 300, message = "Description must be between 10 and 1000 characters")
    @Column(name = "description", length = 300)
    private String description;

    @NotNull(message = "You must agree to the terms and conditions")
    @Column(name = "agreeTerms")
    private Boolean agreeTerms;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "dept_assign")
    private String deptAssign;

    @Column(name = "assign_employee")
    private String assignEmployee;

    @Column(name = "status")
    private String status;

    @Column(name = "complaint_status")
    private String complaintStatus;
}


