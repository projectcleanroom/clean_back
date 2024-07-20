package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.pirtner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime fixed_date;

    @Column(nullable = true, length = 1000)
    private String statement;

}
