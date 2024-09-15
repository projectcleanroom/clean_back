package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PaymentPrepareRequestDto {

    @JsonProperty("merchant_uid")
    private String merchant_uid; // 고객사의 주문 고유 ID
    private int amount;  // 사전 등록할 결제 금액

    public PaymentPrepareRequestDto(String merchant_uid, int amount) {
        this.merchant_uid = merchant_uid;
        this.amount = amount;
    }
}
