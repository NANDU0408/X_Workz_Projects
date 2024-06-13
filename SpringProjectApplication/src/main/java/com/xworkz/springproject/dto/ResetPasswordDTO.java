package com.xworkz.springproject.dto;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDTO {
    @Id
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Password is required")
    private String newPassword;

    @NotEmpty(message = "Password is required")
    private String confirmNewPassword;

}
