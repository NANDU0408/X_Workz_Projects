package com.xworkz.springproject.dto.admin;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

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
@Table(name ="admin_signIn_form")
public class AdminDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @NotNull(message = "Email Address should not be null")
    @Email(message = "Email should be valid")
    @Size(min = 3, max = 30, message = "Email must be between 3 and 30 characters")
    @Column(name = "emailAddress")
    private String emailAddress;

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

    @Transient
    @Column(name = "role") // New field for user role (admin or user)
    private String role;
}
