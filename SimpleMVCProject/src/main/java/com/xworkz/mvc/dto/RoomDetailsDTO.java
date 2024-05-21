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
@Table(name = "RoomDetails ")
public class RoomDetailsDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomNo ")
    private long RoomNo ;
    @Column(name = "floorno ")
    private String FloorNo;
    @Column(name = "RoomType ")
    private double RoomType;
    @Column(name = "Rent ")
    private float Rent ;
    @Column(name = "alloted  ")
    private Enum RoomAllotment  ;
}
