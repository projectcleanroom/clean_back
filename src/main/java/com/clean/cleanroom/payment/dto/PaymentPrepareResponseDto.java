package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class PaymentPrepareResponseDto {

    private final int code;  // 응답 코드 (예: 0 = 성공)
    private final String message;  // 응답 메시지
    private final PrepareResponse response;  // 준비된 결제 응답 데이터

    public PaymentPrepareResponseDto(int code, String message, PrepareResponse response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PrepareResponse {
        private final String merchant_uid;  // 고객사의 주문 고유 ID
        private final int amount;  // 사전 등록된 결제 금액

        public PrepareResponse(String merchant_uid, int amount) {
            this.merchant_uid = merchant_uid;
            this.amount = amount;
        }
    }
}