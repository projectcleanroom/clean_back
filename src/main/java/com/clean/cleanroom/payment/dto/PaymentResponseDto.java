package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PaymentResponseDto {

    @JsonProperty("code")  // 응답 코드 (예: 0 = 성공)
    private final int code;

    @JsonProperty("message")  // 응답 메시지
    private final String message;

    @JsonProperty("response")  // 결제 완료 응답 데이터
    private final PaymentDetail response;

    public PaymentResponseDto(int code, String message, PaymentDetail response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PaymentDetail {

        @JsonProperty("imp_uid")  // Port One의 고유 거래 ID
        private final String impUid;

        @JsonProperty("merchant_uid")  // 고객사의 주문 고유 ID
        private final String merchantUid;

        @JsonProperty("status")  // 결제 상태 (예: "paid", "failed")
        private final String status;

        @JsonProperty("paid_amount")  // 결제된 금액
        private final int paidAmount;

        @JsonProperty("pay_method")  // 결제 방법 (예: "card")
        private final String payMethod;

        public PaymentDetail(String impUid, String merchantUid, String status, int paidAmount, String payMethod) {
            this.impUid = impUid;
            this.merchantUid = merchantUid;
            this.status = status;
            this.paidAmount = paidAmount;
            this.payMethod = payMethod;
        }
    }
}