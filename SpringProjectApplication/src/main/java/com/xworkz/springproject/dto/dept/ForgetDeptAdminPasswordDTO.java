package com.xworkz.springproject.dto.dept;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ForgetDeptAdminPasswordDTO {

    @Id
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String emailAddress;
}
