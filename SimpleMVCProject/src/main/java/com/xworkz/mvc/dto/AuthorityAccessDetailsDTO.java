package com.xworkz.mvc.dto;

import lombok.*;
import sun.security.util.Password;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authority_access_details  ")
public class AuthorityAccessDetailsDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  authority_access_id;
    @Column(name = "name  ")
    private String AuthorityName;
    @Column(name = "age ")
    private int AGE;
    @Column(name = "gender ")
    private String Gender ;
    @Column(name = "dob ")
    private Date DOB  ;
    @Column(name = "address ")
    private String address  ;
    @Column(name = "phone ")
    private long phoneNo  ;
    @Column(name = "adhar")
    private String idProof  ;
    @Column(name = "adharNo")
    private long idProofNo ;
    @Column(name = "emergency_contact")
    private long emergencyContact ;
}
