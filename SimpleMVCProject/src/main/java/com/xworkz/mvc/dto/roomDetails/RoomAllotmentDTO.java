package com.xworkz.mvc.dto.roomDetails;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Room_Allotment  ")
public class RoomAllotmentDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allotment_id ")
    private long Allotment_ID ;
    @Column(name = "customer_id ")
    private long customer_id;
    @Column(name = "RoomNo ")
    private long RoomNo;
    @Column(name = "Entry_date ")
    private Date Entry_date ;
    @Column(name = "vacate_date  ")
    private Date vacate_date  ;
    @Column(name = "Amount_to_pay  ")
    private double amount_to_pay  ;
    @Column(name = "Payed_Amount  ")
    private double payed_Amount  ;
}
