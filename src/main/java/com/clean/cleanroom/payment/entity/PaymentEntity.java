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
    private String imp_uid; // 포트원 결제 고유번호

    @Column(nullable = false)
    private String pg_provider; // 스마트로

    @Column(nullable = false, length = 255)
    private String name; // 상품: 클린타입

    @Column(nullable = false)
    private int amount;  // 결제 금액


    @Column(nullable = false, name = "merchant_uid")
    private String merchant_uid; // 주문명 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatusType status;  // 결제 상태 (예: 성공, 실패, 취소 등)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayMethod pay_method;  // 결제 유형 (예: 신용카드, 계좌이체 등)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transaction_date;  // 결제 일자

    @Column(nullable = false)
    private String buyer_email; // 구매자 이메일

    @Column(nullable = false)
    private String buyer_tel; // 구매자 전화번호

    @Column(nullable = false)
    private String buyer_name;  // 새로운 필드: 구매자 이름

    private boolean is_request_cancelled;  // 결제 요청이 취소되었는지 여부

    @ManyToOne(fetch = FetchType.LAZY)  // 견적과의 다대일 관계 설정
    @JoinColumn(name = "estimate_id")
    private Estimate estimate;  // 결제와 연결된 견적

    @ManyToOne(fetch = FetchType.LAZY)  // 의뢰와의 다대일 관계 설정
    @JoinColumn(name = "commission_id")
    private Commission commission;  // 결제와 연결된 의뢰

    @Builder
    public PaymentEntity(String imp_uid, String pg_provider, String name, int amount, String merchant_uid, PaymentStatusType status,
                         PayMethod pay_method, Date transaction_date, String buyer_email, String buyer_tel, String buyer_name,
                         boolean is_request_cancelled, Estimate estimate, Commission commission) {

        this.imp_uid = imp_uid;
        this.pg_provider = pg_provider;
        this.name = name;
        this.amount = amount;
        this.merchant_uid = merchant_uid;
        this.status = status;
        this.pay_method = pay_method;
        this.transaction_date = transaction_date;
        this.buyer_email = buyer_email;
        this.buyer_tel = buyer_tel;
        this.buyer_name = buyer_name;
        this.is_request_cancelled = is_request_cancelled;
        this.estimate = estimate;
        this.commission = commission;
    }

    // 도메인 메서드 (비즈니스 로직용)
    public void cancelRequest() {
        this.is_request_cancelled = true;  // 결제 요청 취소 상태로 설정
        this.status = PaymentStatusType.CANCELLED;  // 결제 상태도 취소로 변경
    }

    public void completePayment() {
        this.status = PaymentStatusType.PAID;  // 결제 상태를 완료로 설정
    }

    public void failPayment() {
        this.status = PaymentStatusType.FAILED;  // 결제 상태를 실패로 설정
    }
}