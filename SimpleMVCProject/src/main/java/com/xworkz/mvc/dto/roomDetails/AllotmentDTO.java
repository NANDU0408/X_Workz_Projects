package com.xworkz.mvc.dto.roomDetails;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllotmentDTO {
    private int customerId;
    private int roomNo;
    private Date entryDate;
    private Date vacateDate;
    private double amountToPay;
    private double payedAmount;
}
