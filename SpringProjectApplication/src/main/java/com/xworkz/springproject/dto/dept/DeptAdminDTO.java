package com.xworkz.springproject.dto.dept;

import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
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
@Table(name ="deptAdmin_signIn_form")
public class DeptAdminDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dept_id")
    private int dept_id;

    @NotNull(message = "First Name should not be null")
    @Size(min = 3, max = 30, message = "First Name must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "First Name can only contain letters, spaces, and hyphens")
    @Column(name = "firstName")
    private String firstName;

    @NotNull(message = "Last Name should not be null")
    @Size(min = 3, max = 30, message = "Last Name must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Last Name can only contain letters, spaces, and hyphens")
    @Column(name = "lastName")
    private String lastName;

    @NotNull(message = "Full Name should not be null")
    @Size(min = 3, max = 30, message = "Full Name must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "Full Name can only contain letters, spaces, and hyphens")
    @Column(name = "designation")
    private String designation;

    @NotNull(message = "Email Address should not be null")
    @Email(message = "Email should be valid")
    @Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters")
    @Column(name = "emailAddress")
    private String emailAddress;

    @NotNull(message = "Phone Number should not be null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must be 10 digits")
    @Column(name = "phoneNumber")
    private String mobileNumber;

    @NotNull(message = "You must agree to the terms and conditions")
    @Column(name = "agreeTerms")
    private Boolean agreeTerms;

    @Column(name = "password")
    private String password;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "count")
    private int count;

    @Column(name = "failed_attempts_count")
    private int failedAttemptsCount;

    @Column(name = "failed_attempt_dateTime")
    private LocalDateTime failedAttemptDateTime;

    @Transient
    private boolean accountLocked;

    @Transient
    @Column(name = "role") // New field for user role (admin or user)
    private String role;
}
