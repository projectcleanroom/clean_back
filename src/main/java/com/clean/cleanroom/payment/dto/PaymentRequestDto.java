package com.clean.cleanroom.payment.dto;

import com.clean.cleanroom.enums.PayMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PaymentRequestDto {

    @JsonProperty("imp_uid")  // Port One의 고유 거래 ID
    private final String impUid;

    @JsonProperty("merchant_uid")  // 고객사의 주문 고유 ID
    private final String merchantUid;

    @JsonProperty("pay_method")  // 결제 방법 (예: "카드")
    private final PayMethod payMethod;

    @JsonProperty("buyer_email")  // 구매자 이메일
    private final String buyerEmail;

    @JsonProperty("buyer_tel")  // 구매자 전화번호
    private final String buyerTel;

    public PaymentRequestDto(String impUid, String merchantUid, PayMethod payMethod, String buyerEmail, String buyerTel) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
    }
}
