package com.clean.cleanroom.partner.entity;

import com.clean.cleanroom.enums.PartnerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 15)
    private String managerName;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 15)
    private String businessType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PartnerType partnerType;

}