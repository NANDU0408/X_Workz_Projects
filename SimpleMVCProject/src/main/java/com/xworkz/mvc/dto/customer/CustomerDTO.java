package com.xworkz.mvc.dto.customer;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer")
public class CustomerDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;
    @Column(name = "name")
    private String CNAME;
    @Column(name = "age")
    private int AGE;
    @Column(name = "gender ")
    private String Gender ;
    @Column(name = "dob ")
    private LocalDate DOB  ;
    @Column(name = "address ")
    private String Address  ;
    @Column(name = "phone ")
    private long PhoneNo  ;
    @Column(name = "adhar")
    private String IDProof  ;
    @Column(name = "adharNo")
    private long IDProofNo ;
    @Column(name = "emergency_contact")
    private long EmergencyContact ;
}
