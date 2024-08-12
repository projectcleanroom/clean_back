package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.partner.entity.Partner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "commission_id")
    private Commission commissionId;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partnerId;

    @Column(nullable = false)
    @Comment("가격")
    private int price;

    @Column(nullable = true)
    @Comment("확정 일자")
    private LocalDateTime fixedDate;

    @Column(nullable = true, length = 1000)
    @Comment("특이사항")
    private String statement;

    @Comment("승인 상태")
    private boolean approved = false;


    // 승인 상태 변경 메서드
    public void approve() {
        this.approved = true;
    }
}
