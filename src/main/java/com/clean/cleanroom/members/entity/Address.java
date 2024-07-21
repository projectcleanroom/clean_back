package com.clean.cleanroom.members.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members members;

    @Column(nullable = false, length = 30)
    private String address;

}
