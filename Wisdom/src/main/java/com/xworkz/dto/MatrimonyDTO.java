package com.xworkz.dto;

import lombok.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatrimonyDTO {

    private String name;
    private int age;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String job;
    private String qualification;
    private String lookingFor;
}
