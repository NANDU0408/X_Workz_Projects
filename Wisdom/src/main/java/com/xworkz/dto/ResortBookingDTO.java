package com.xworkz.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResortBookingDTO {

    private String name;
    private String email;
    private String resort;
    private String roomType;
    private String checkin;
    private String checkout;
}
