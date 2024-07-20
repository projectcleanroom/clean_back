package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.estimate.entity.Estimate;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
public class MemberOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    private Estimate estimateId;

}
