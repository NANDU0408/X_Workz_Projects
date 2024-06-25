package com.xworkz.springproject.dto.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditComplaintDTO {

    private int complaintId;
    private String complaintType;
    private String country;
    private String state;
    private String city;
    private String address;

    @Size(min = 5, max = 300, message = "Description must be between 10 and 1000 characters")
    private String description;

    private Boolean agreeTerms;
    private int userId;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private String status;

}
