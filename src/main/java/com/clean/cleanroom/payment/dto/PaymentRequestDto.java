package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class PaymentRequestDto {

    private final String impUid;  // Port One의 고유 거래 ID
    private final String merchantUid;  // 고객사의 주문 고유 ID
    private final String payMethod;  // 결제 방법 (예: "카드")
    private final String buyerEmail;  // 구매자 이메일
    private final String buyerTel;  // 구매자 전화번호

    public PaymentRequestDto(String impUid, String merchantUid, String payMethod, String buyerEmail, String buyerTel) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
    }
}
