package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class CancelPaymentRequestDto {

    private final String imp_uid;  // Port One 결제 고유 번호
    private final String merchant_uid;  // 고객이 보낸 거래 식별 번호
    private final Double amount;  // (부분) 취소 요청 금액
    private final Double tax_free;  // 취소 요청 금액 중 비과세 금액
    private final Double vat_amount;  // 취소 요청 금액 중 부가세 금액
    private final Double check_sum;  // 취소 전 취소 가능 잔액
    private final String reason;  // 취소 사유
    private final String refund_holder;  // 환불 계좌 예금주 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refund_bank;  // 환불 계좌 은행 코드 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refund_account;  // 환불 계좌 번호 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refund_tel;  // 환불 계좌 소유자 연락처 (가상 계좌 및 스마트로 - 구 모듈 취소 시 필수)
    private final String extra;  // 추가 옵션 (여러 값을 줄바꿈으로 제공)

    public CancelPaymentRequestDto(String imp_uid, String merchant_uid, Double amount, Double tax_free, Double vat_amount, Double check_sum,
                                   String reason, String refund_holder, String refund_bank, String refund_account, String refund_tel, String extra) {
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
        this.amount = amount;
        this.tax_free = tax_free;
        this.vat_amount = vat_amount;
        this.check_sum = check_sum;
        this.reason = reason;
        this.refund_holder = refund_holder;
        this.refund_bank = refund_bank;
        this.refund_account = refund_account;
        this.refund_tel = refund_tel;
        this.extra = extra;
    }
}