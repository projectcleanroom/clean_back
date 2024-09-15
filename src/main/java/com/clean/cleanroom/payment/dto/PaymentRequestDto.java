package com.clean.cleanroom.payment.dto;

import com.clean.cleanroom.enums.PayMethod;
import lombok.Getter;

@Getter
public class PaymentRequestDto {

    private final String imp_uid;  // Port One의 고유 거래 ID
    private final String merchant_uid;  // 고객사의 주문 고유 ID
    private final PayMethod pay_method;  // 결제 방법 (예: "카드")
    private final String buyer_email;  // 구매자 이메일
    private final String buyer_tel;  // 구매자 전화번호

    public PaymentRequestDto(String imp_uid, String merchant_uid, PayMethod pay_method, String buyer_email, String buyer_tel) {
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.pay_method = pay_method;
        this.buyer_email = buyer_email;
        this.buyer_tel = buyer_tel;
    }
}