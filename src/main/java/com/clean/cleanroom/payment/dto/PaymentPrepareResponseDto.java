package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PaymentPrepareResponseDto {

    @JsonProperty("code")  // 응답 코드 (예: 0 = 성공)
    private final int code;

    @JsonProperty("message")  // 응답 메시지
    private final String message;

    @JsonProperty("response")  // 준비된 결제 응답 데이터
    private final PrepareResponse response;

    public PaymentPrepareResponseDto(int code, String message, PrepareResponse response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PrepareResponse {

        @JsonProperty("merchant_uid")  // 고객사의 주문 고유 ID
        private final String merchantUid;

        @JsonProperty("amount")  // 사전 등록된 결제 금액
        private final int amount;

        public PrepareResponse(String merchantUid, int amount) {
            this.merchantUid = merchantUid;
            this.amount = amount;
        }
    }
}
