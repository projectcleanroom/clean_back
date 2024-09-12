package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class CancelPaymentRequestDto {

    private final String impUid;  // Port One 결제 고유 번호
    private final String merchantUid;  // 고객이 보낸 거래 식별 번호
    private final Double amount;  // (부분) 취소 요청 금액
    private final Double taxFree;  // 취소 요청 금액 중 비과세 금액
    private final Double vatAmount;  // 취소 요청 금액 중 부가세 금액
    private final Double checkSum;  // 취소 전 취소 가능 잔액
    private final String reason;  // 취소 사유
    private final String refundHolder;  // 환불 계좌 예금주 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refundBank;  // 환불 계좌 은행 코드 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refundAccount;  // 환불 계좌 번호 (가상 계좌 취소 및 휴대폰 소액결제 환불 시 필수)
    private final String refundTel;  // 환불 계좌 소유자 연락처 (가상 계좌 및 스마트로 - 구 모듈 취소 시 필수)
    private final String extra;  // 추가 옵션 (여러 값을 줄바꿈으로 제공)

    public CancelPaymentRequestDto(String impUid, String merchantUid, Double amount, Double taxFree, Double vatAmount, Double checkSum,
                                   String reason, String refundHolder, String refundBank, String refundAccount, String refundTel, String extra) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.amount = amount;
        this.taxFree = taxFree;
        this.vatAmount = vatAmount;
        this.checkSum = checkSum;
        this.reason = reason;
        this.refundHolder = refundHolder;
        this.refundBank = refundBank;
        this.refundAccount = refundAccount;
        this.refundTel = refundTel;
        this.extra = extra;
    }
}