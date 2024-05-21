package com.xworkz.mvc.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer_login  ")
public class CustomerLoginDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private long customerId;
    @Column(name = "username")
    private String customerName;
    @Column(name = "secret")
    private String password;
}
