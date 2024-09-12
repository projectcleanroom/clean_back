package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class PaymentPrepareRequestDto {

    @JsonProperty("merchant_uid")
    private String merchantUid; // 고객사의 주문 고유 ID
    private int amount;  // 사전 등록할 결제 금액

    public PaymentPrepareRequestDto(String merchantUid, int amount) {
        this.merchantUid = merchantUid;
        this.amount = amount;
    }
}