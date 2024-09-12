package com.clean.cleanroom.payment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaymentDetailResponseDto {

    private final int code;  // 응답 코드 (예: 0 = 성공)
    private final String message;  // 응답 메시지
    private final PaymentDetail response;  // 결제 내역 응답 데이터

    public PaymentDetailResponseDto(int code, String message, PaymentDetail response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PaymentDetail {
        private final String impUid;  // 포트원의 고유 거래 ID
        private final String merchantUid;  // 고객사의 주문 고유 ID
        private final String payMethod;  // 결제 방법 (예: "카드")
        private final String channel;  // 결제 채널
        private final String pgProvider;  // PG사 제공자
        private final String embPgProvider;  // 임베디드 PG사 제공자
        private final String pgTid;  // PG사의 트랜잭션 ID
        private final String pgId;  // PG사의 ID
        private final boolean escrow;  // 에스크로 사용 여부
        private final String applyNum;  // 카드 승인 번호
        private final String bankCode;  // 은행 코드
        private final String bankName;  // 은행 이름
        private final String cardCode;  // 카드 코드
        private final String cardName;  // 카드 이름
        private final String cardIssuerCode;  // 카드 발급자 코드
        private final String cardIssuerName;  // 카드 발급자 이름
        private final String cardPublisherCode;  // 카드 발행사 코드
        private final String cardPublisherName;  // 카드 발행사 이름
        private final int cardQuota;  // 카드 할부 개월 수
        private final String cardNumber;  // 카드 번호 (마스킹된 형태)
        private final int cardType;  // 카드 타입 (예: 신용카드, 체크카드)
        private final String vbankCode;  // 가상 계좌 은행 코드
        private final String vbankName;  // 가상 계좌 은행 이름
        private final String vbankNum;  // 가상 계좌 번호
        private final String vbankHolder;  // 가상 계좌 예금주
        private final long vbankDate;  // 가상 계좌 입금 기한 (Unix 타임스탬프)
        private final long vbankIssuedAt;  // 가상 계좌 발급 시각 (Unix 타임스탬프)
        private final String name;  // 결제 상품명
        private final int amount;  // 결제 금액
        private final int cancelAmount;  // 취소된 금액
        private final String currency;  // 통화 (예: "KRW")
        private final String buyerName;  // 구매자 이름
        private final String buyerEmail;  // 구매자 이메일
        private final String buyerTel;  // 구매자 전화번호
        private final String buyerAddr;  // 구매자 주소
        private final String buyerPostcode;  // 구매자 우편번호
        private final String customData;  // 커스텀 데이터
        private final String userAgent;  // 사용자 에이전트
        private final String status;  // 결제 상태 (예: "paid", "failed")
        private final long startedAt;  // 결제 시작 시각 (Unix 타임스탬프)
        private final long paidAt;  // 결제 완료 시각 (Unix 타임스탬프)
        private final long failedAt;  // 결제 실패 시각 (Unix 타임스탬프)
        private final long cancelledAt;  // 결제 취소 시각 (Unix 타임스탬프)
        private final String failReason;  // 결제 실패 사유
        private final String cancelReason;  // 결제 취소 사유
        private final String receiptUrl;  // 영수증 URL
        private final List<CancelHistory> cancelHistory;  // 취소 내역
        private final List<String> cancelReceiptUrls;  // 취소 영수증 URL 리스트
        private final boolean cashReceiptIssued;  // 현금영수증 발행 여부
        private final String customerUid;  // 고객 고유 ID
        private final String customerUidUsage;  // 고객 고유 ID 사용 용도
        private final Promotion promotion;  // 프로모션 정보
        private final String transactionDate;  // 결제 일자  <--- 새로 추가된 필드

        public PaymentDetail(String impUid, String merchantUid, String payMethod, String channel, String pgProvider, String embPgProvider,
                             String pgTid, String pgId, boolean escrow, String applyNum, String bankCode, String bankName, String cardCode,
                             String cardName, String cardIssuerCode, String cardIssuerName, String cardPublisherCode, String cardPublisherName,
                             int cardQuota, String cardNumber, int cardType, String vbankCode, String vbankName, String vbankNum,
                             String vbankHolder, long vbankDate, long vbankIssuedAt, String name, int amount, int cancelAmount, String currency,
                             String buyerName, String buyerEmail, String buyerTel, String buyerAddr, String buyerPostcode, String customData,
                             String userAgent, String status, long startedAt, long paidAt, long failedAt, long cancelledAt, String failReason,
                             String cancelReason, String receiptUrl, List<CancelHistory> cancelHistory, List<String> cancelReceiptUrls,
                             boolean cashReceiptIssued, String customerUid, String customerUidUsage, Promotion promotion, String transactionDate) {  // 새로 추가된 필드 반영
            this.impUid = impUid;
            this.merchantUid = merchantUid;
            this.payMethod = payMethod;
            this.channel = channel;
            this.pgProvider = pgProvider;
            this.embPgProvider = embPgProvider;
            this.pgTid = pgTid;
            this.pgId = pgId;
            this.escrow = escrow;
            this.applyNum = applyNum;
            this.bankCode = bankCode;
            this.bankName = bankName;
            this.cardCode = cardCode;
            this.cardName = cardName;
            this.cardIssuerCode = cardIssuerCode;
            this.cardIssuerName = cardIssuerName;
            this.cardPublisherCode = cardPublisherCode;
            this.cardPublisherName = cardPublisherName;
            this.cardQuota = cardQuota;
            this.cardNumber = cardNumber;
            this.cardType = cardType;
            this.vbankCode = vbankCode;
            this.vbankName = vbankName;
            this.vbankNum = vbankNum;
            this.vbankHolder = vbankHolder;
            this.vbankDate = vbankDate;
            this.vbankIssuedAt = vbankIssuedAt;
            this.name = name;
            this.amount = amount;
            this.cancelAmount = cancelAmount;
            this.currency = currency;
            this.buyerName = buyerName;
            this.buyerEmail = buyerEmail;
            this.buyerTel = buyerTel;
            this.buyerAddr = buyerAddr;
            this.buyerPostcode = buyerPostcode;
            this.customData = customData;
            this.userAgent = userAgent;
            this.status = status;
            this.startedAt = startedAt;
            this.paidAt = paidAt;
            this.failedAt = failedAt;
            this.cancelledAt = cancelledAt;
            this.failReason = failReason;
            this.cancelReason = cancelReason;
            this.receiptUrl = receiptUrl;
            this.cancelHistory = cancelHistory;
            this.cancelReceiptUrls = cancelReceiptUrls;
            this.cashReceiptIssued = cashReceiptIssued;
            this.customerUid = customerUid;
            this.customerUidUsage = customerUidUsage;
            this.promotion = promotion;
            this.transactionDate = transactionDate;  // 새로 추가된 필드 설정
        }
    }

    @Getter
    public static class CancelHistory {
        private final String pgTid;  // 취소된 거래의 PG사 트랜잭션 ID
        private final int amount;  // 취소된 금액
        private final long cancelledAt;  // 취소 시각 (Unix 타임스탬프)
        private final String reason;  // 취소 사유
        private final String cancellationId;  // 취소 ID
        private final String receiptUrl;  // 취소 영수증 URL

        public CancelHistory(String pgTid, int amount, long cancelledAt, String reason, String cancellationId, String receiptUrl) {
            this.pgTid = pgTid;
            this.amount = amount;
            this.cancelledAt = cancelledAt;
            this.reason = reason;
            this.cancellationId = cancellationId;
            this.receiptUrl = receiptUrl;
        }
    }

    @Getter
    public static class Promotion {
        private final String id;  // 프로모션 ID
        private final int discount;  // 프로모션 할인 금액

        public Promotion(String id, int discount) {
            this.id = id;
            this.discount = discount;
        }
    }
}