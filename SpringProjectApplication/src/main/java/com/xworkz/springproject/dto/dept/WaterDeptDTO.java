package com.xworkz.springproject.dto.dept;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "water_dept")
public class WaterDeptDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deptId")
    private int deptId;

    @NotNull(message = "Department Name should not be null")
    @Size(min = 3, max = 50, message = "Department Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Department Name can only contain letters, spaces, and hyphens")
    @Column(name = "deptName")
    private String deptName;

    @NotNull(message = "Location should not be null")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters")
    @Column(name = "area")
    private String area;

    @NotNull(message = "Location should not be null")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters")
    @Column(name = "address")
    private String address;

    @NotNull(message = "Number of Employees should not be null")
    @Min(value = 1, message = "Number of Employees must be at least 1")
    @Column(name = "numberOfEmployees")
    private int numberOfEmployees;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;
}
