package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    private Commission commissionId;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partnerId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private LocalDateTime fixedDate;

    @Column(nullable = true, length = 1000)
    private String statement;

}
