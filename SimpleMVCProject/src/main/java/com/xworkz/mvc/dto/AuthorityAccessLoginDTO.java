package com.xworkz.mvc.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authority_access_login")
public class AuthorityAccessLoginDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authority_access_id")
    private long authorityAccessId;
    @Column(name = "username")
    private String authorityName;
    @Column(name = "secret")
    private String password;
}
