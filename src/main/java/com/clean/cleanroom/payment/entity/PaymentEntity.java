package com.clean.cleanroom.payment.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.PaymentStatusType;
import com.clean.cleanroom.enums.PayMethod;
import com.clean.cleanroom.estimate.entity.Estimate;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 결제 ID

    @Column(nullable = false)
    private String impUid; // 포트원 결제 고유번호

    @Column(nullable = false)
    private String pgProvider; // 스마트로

    @Column(nullable = false, length = 255)
    private String name; // 상품: 클린타입

    @Column(nullable = false)
    private int amount;  // 결제 금액

    @Column(nullable = false)
    private String merchantUid; // 주문명 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatusType status;  // 결제 상태 (예: 성공, 실패, 취소 등)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayMethod payMethod;  // 결제 유형 (예: 신용카드, 계좌이체 등)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;  // 결제 일자

    @Column(nullable = false)
    private String buyerEmail; // 구매자 이메일

    @Column(nullable = false)
    private String buyerTel; // 구매자 전화번호

    @Column(nullable = false)
    private String buyerName;  // 새로운 필드: 구매자 이름

    private boolean isRequestCancelled;  // 결제 요청이 취소되었는지 여부

    @ManyToOne(fetch = FetchType.LAZY)  // 견적과의 다대일 관계 설정
    @JoinColumn(name = "estimate_id")
    private Estimate estimate;  // 결제와 연결된 견적

    @ManyToOne(fetch = FetchType.LAZY)  // 의뢰와의 다대일 관계 설정
    @JoinColumn(name = "commission_id")
    private Commission commission;  // 결제와 연결된 의뢰

    @Builder
    public PaymentEntity(String impUid, String pgProvider, String name, int amount, String merchantUid, PaymentStatusType status,
                         PayMethod payMethod, Date transactionDate, String buyerEmail, String buyerTel, String buyerName,
                         boolean isRequestCancelled, Estimate estimate, Commission commission) {

        this.impUid = impUid;
        this.pgProvider = pgProvider;
        this.name = name;
        this.amount = amount;
        this.merchantUid = merchantUid;
        this.status = status;
        this.payMethod = payMethod;
        this.transactionDate = transactionDate;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
        this.buyerName = buyerName;
        this.isRequestCancelled = isRequestCancelled;
        this.estimate = estimate;
        this.commission = commission;
    }

    // 도메인 메서드 (비즈니스 로직용)
    public void cancelRequest() {
        this.isRequestCancelled = true;  // 결제 요청 취소 상태로 설정
        this.status = PaymentStatusType.CANCELLED;  // 결제 상태도 취소로 변경
    }

    public void completePayment() {
        this.status = PaymentStatusType.PAID;  // 결제 상태를 완료로 설정
    }

    public void failPayment() {
        this.status = PaymentStatusType.FAILED;  // 결제 상태를 실패로 설정
    }
}