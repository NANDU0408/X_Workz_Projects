package com.xworkz.springproject.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditFileDTO {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String mobileNumber;
    private MultipartFile profilePicture;
    private String profilePicturePath;

}
