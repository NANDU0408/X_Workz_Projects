package com.xworkz.springproject.dto.user;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SignInDTO {

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String emailAddress;

//    @NotEmpty(message = "Password is required")
    private String password;

//    @Column(name = "failed_attempts_count")
    private int failedAttemptsCount;

//    @Column(name = "failed_attempt_dateTime")
    private LocalDateTime failedAttemptDateTime;

//    @NotEmpty(message = "UpdatedPassword is required")
//    @Column(name = "updatedPassword")
    private String updatedPassword;
}
