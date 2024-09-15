package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentPrepareRequestDto {

    @JsonProperty("merchant_uid")  // 고객사의 주문 고유 ID
    private String merchantUid;

    @JsonProperty("amount")  // 사전 등록할 결제 금액
    private int amount;

    public PaymentPrepareRequestDto(String merchantUid, int amount) {
        this.merchantUid = merchantUid;
        this.amount = amount;
    }
}