package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class PaymentResponseDto {

    private final int code;  // 응답 코드 (예: 0 = 성공)
    private final String message;  // 응답 메시지
    private final PaymentDetail response;  // 결제 완료 응답 데이터

    public PaymentResponseDto(int code, String message, PaymentDetail response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PaymentDetail {
        private final String imp_uid;  // Port One의 고유 거래 ID
        private final String merchant_uid;  // 고객사의 주문 고유 ID
        private final String status;  // 결제 상태 (예: "paid", "failed")
        private final int paid_amount;  // 결제된 금액
        private final String pay_method;  // 결제 방법 (예: "card")

        public PaymentDetail(String imp_uid, String merchant_uid, String status, int paid_amount, String pay_method) {
            this.imp_uid = imp_uid;
            this.merchant_uid = merchant_uid;
            this.status = status;
            this.paid_amount = paid_amount;
            this.pay_method = pay_method;
        }
    }
}