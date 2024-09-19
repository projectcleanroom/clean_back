package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.payment.entity.PaymentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;  // 이 견적과 연결된 결제 목록

    @ManyToOne(fetch = FetchType.LAZY)  // Members와의 다대일 관계 설정
    @JoinColumn(name = "members_id")
    private Members member;  // 연관 관계 설정

    @ManyToOne
    @JoinColumn(name = "commission_id")
    private Commission commission;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(nullable = false)
    @Comment("확정 가격")
    private int price;

    @Column(nullable = false)
    @Comment("임시 가격")
    private int tmpPrice;

    @Column(nullable = true)
    @Comment("확정 일자")
    private LocalDateTime fixedDate;

    @Column(nullable = true, length = 1000)
    @Comment("특이사항")
    private String statement;

    @Comment("승인 상태")
    private boolean approved = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("견적 상태")
    private StatusType status;

    // 승인 상태 변경 메서드
    public void approve() {
        this.approved = true;
    }
}