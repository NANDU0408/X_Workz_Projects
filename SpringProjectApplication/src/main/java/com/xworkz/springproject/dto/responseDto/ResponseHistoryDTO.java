package com.xworkz.springproject.dto.responseDto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseHistoryDTO {

    private int id;

    private int complaintId;

    private String departmentName;

    private String complaintStatus;
}
