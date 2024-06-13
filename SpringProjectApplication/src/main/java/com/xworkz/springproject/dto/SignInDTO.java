package com.xworkz.springproject.dto;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name ="signUp_form")
public class SignInDTO {


    @Id
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String emailAddress;

    @NotEmpty(message = "Password is required")
    private String password;

    @Column(name = "updatedPassword")
    private String updatedPassword;
}
