package com.xworkz.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDTO {

    private String name;
    private String email;
    private long mobile;
    private String comments;
}
