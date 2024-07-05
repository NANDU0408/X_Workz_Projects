package com.xworkz.springproject.dto.dept;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeptAdminSignInDTO {

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String emailAddress;

    private String password;
}
