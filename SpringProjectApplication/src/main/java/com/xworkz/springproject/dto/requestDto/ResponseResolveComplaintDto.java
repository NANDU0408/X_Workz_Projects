package com.xworkz.springproject.dto.requestDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseResolveComplaintDto {
    private boolean status;
    private String message;
}