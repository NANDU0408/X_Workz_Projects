package com.xworkz.springproject.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserProfileDTO {

    private String profilePicturePath;
    private MultipartFile profilePicture;
    private String firstName;
    private String lastName;
}
