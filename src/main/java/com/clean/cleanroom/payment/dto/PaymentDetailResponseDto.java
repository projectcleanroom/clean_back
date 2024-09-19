package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentDetailResponseDto {

    @JsonProperty("code")  // 응답 코드 (예: 0 = 성공)
    private final int code;

    @JsonProperty("message")  // 응답 메시지
    private final String message;

    @JsonProperty("response")  // 결제 내역 응답 데이터
    private final PaymentDetail response;

    public PaymentDetailResponseDto(int code, String message, PaymentDetail response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class PaymentDetail {

        @JsonProperty("imp_uid")  // 포트원의 고유 거래 ID
        private final String impUid;

        @JsonProperty("merchant_uid")  // 고객사의 주문 고유 ID
        private final String merchantUid;

        @JsonProperty("pay_method")  // 결제 방법 (예: "카드")
        private final String payMethod;

        @JsonProperty("channel")  // 결제 채널
        private final String channel;

        @JsonProperty("pg_provider")  // PG사 제공자
        private final String pgProvider;

        @JsonProperty("emb_pg_provider")  // 임베디드 PG사 제공자
        private final String embPgProvider;

        @JsonProperty("pg_tid")  // PG사의 트랜잭션 ID
        private final String pgTid;

        @JsonProperty("pg_id")  // PG사의 ID
        private final String pgId;

        @JsonProperty("escrow")  // 에스크로 사용 여부
        private final boolean escrow;

        @JsonProperty("apply_num")  // 카드 승인 번호
        private final String applyNum;

        @JsonProperty("bank_code")  // 은행 코드
        private final String bankCode;

        @JsonProperty("bank_name")  // 은행 이름
        private final String bankName;

        @JsonProperty("card_code")  // 카드 코드
        private final String cardCode;

        @JsonProperty("card_name")  // 카드 이름
        private final String cardName;

        @JsonProperty("card_issuer_code")  // 카드 발급자 코드
        private final String cardIssuerCode;

        @JsonProperty("card_issuer_name")  // 카드 발급자 이름
        private final String cardIssuerName;

        @JsonProperty("card_publisher_code")  // 카드 발행사 코드
        private final String cardPublisherCode;

        @JsonProperty("card_publisher_name")  // 카드 발행사 이름
        private final String cardPublisherName;

        @JsonProperty("card_quota")  // 카드 할부 개월 수
        private final int cardQuota;

        @JsonProperty("card_number")  // 카드 번호 (마스킹된 형태)
        private final String cardNumber;

        @JsonProperty("card_type")  // 카드 타입 (예: 신용카드, 체크카드)
        private final int cardType;

        @JsonProperty("vbank_code")  // 가상 계좌 은행 코드
        private final String vbankCode;

        @JsonProperty("vbank_name")  // 가상 계좌 은행 이름
        private final String vbankName;

        @JsonProperty("vbank_num")  // 가상 계좌 번호
        private final String vbankNum;

        @JsonProperty("vbank_holder")  // 가상 계좌 예금주
        private final String vbankHolder;

        @JsonProperty("vbank_date")  // 가상 계좌 입금 기한 (Unix 타임스탬프)
        private final long vbankDate;

        @JsonProperty("vbank_issued_at")  // 가상 계좌 발급 시각 (Unix 타임스탬프)
        private final long vbankIssuedAt;

        @JsonProperty("name")  // 결제 상품명
        private final String name;

        @JsonProperty("amount")  // 결제 금액
        private final int amount;

        @JsonProperty("cancel_amount")  // 취소된 금액
        private final int cancelAmount;

        @JsonProperty("currency")  // 통화 (예: "KRW")
        private final String currency;

        @JsonProperty("buyer_name")  // 구매자 이름
        private final String buyerName;

        @JsonProperty("buyer_email")  // 구매자 이메일
        private final String buyerEmail;

        @JsonProperty("buyer_tel")  // 구매자 전화번호
        private final String buyerTel;

        @JsonProperty("buyer_addr")  // 구매자 주소
        private final String buyerAddr;

        @JsonProperty("buyer_postcode")  // 구매자 우편번호
        private final String buyerPostcode;

        @JsonProperty("custom_data")  // 커스텀 데이터
        private final String customData;

        @JsonProperty("user_agent")  // 사용자 에이전트
        private final String userAgent;

        @JsonProperty("status")  // 결제 상태 (예: "paid", "failed")
        private final String status;

        @JsonProperty("started_at")  // 결제 시작 시각 (Unix 타임스탬프)
        private final long startedAt;

        @JsonProperty("paid_at")  // 결제 완료 시각 (Unix 타임스탬프)
        private final long paidAt;

        @JsonProperty("failed_at")  // 결제 실패 시각 (Unix 타임스탬프)
        private final long failedAt;

        @JsonProperty("cancelled_at")  // 결제 취소 시각 (Unix 타임스탬프)
        private final long cancelledAt;

        @JsonProperty("fail_reason")  // 결제 실패 사유
        private final String failReason;

        @JsonProperty("cancel_reason")  // 결제 취소 사유
        private final String cancelReason;

        @JsonProperty("receipt_url")  // 영수증 URL
        private final String receiptUrl;

        @JsonProperty("cancel_history")  // 취소 내역
        private final List<CancelHistory> cancelHistory;

        @JsonProperty("cancel_receipt_urls")  // 취소 영수증 URL 리스트
        private final List<String> cancelReceiptUrls;

        @JsonProperty("cash_receipt_issued")  // 현금영수증 발행 여부
        private final boolean cashReceiptIssued;

        @JsonProperty("customer_uid")  // 고객 고유 ID
        private final String customerUid;

        @JsonProperty("customer_uid_usage")  // 고객 고유 ID 사용 용도
        private final String customerUidUsage;

        @JsonProperty("promotion")  // 프로모션 정보
        private final Promotion promotion;

        @JsonProperty("transaction_date")  // 결제 일자
        private final String transactionDate;  // 새로 추가된 필드

        public PaymentDetail(String impUid, String merchantUid, String payMethod, String channel, String pgProvider, String embPgProvider,
                             String pgTid, String pgId, boolean escrow, String applyNum, String bankCode, String bankName, String cardCode,
                             String cardName, String cardIssuerCode, String cardIssuerName, String cardPublisherCode, String cardPublisherName,
                             int cardQuota, String cardNumber, int cardType, String vbankCode, String vbankName, String vbankNum,
                             String vbankHolder, long vbankDate, long vbankIssuedAt, String name, int amount, int cancelAmount, String currency,
                             String buyerName, String buyerEmail, String buyerTel, String buyerAddr, String buyerPostcode, String customData,
                             String userAgent, String status, long startedAt, long paidAt, long failedAt, long cancelledAt, String failReason,
                             String cancelReason, String receiptUrl, List<CancelHistory> cancelHistory, List<String> cancelReceiptUrls,
                             boolean cashReceiptIssued, String customerUid, String customerUidUsage, Promotion promotion, String transactionDate) {
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

        @JsonProperty("pg_tid")  // 취소된 거래의 PG사 트랜잭션 ID
        private final String pgTid;

        @JsonProperty("amount")  // 취소된 금액
        private final int amount;

        @JsonProperty("cancelled_at")  // 취소 시각 (Unix 타임스탬프)
        private final long cancelledAt;

        @JsonProperty("reason")  // 취소 사유
        private final String reason;

        @JsonProperty("cancellation_id")  // 취소 ID
        private final String cancellationId;

        @JsonProperty("receipt_url")  // 취소 영수증 URL
        private final String receiptUrl;

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

        @JsonProperty("id")  // 프로모션 ID
        private final String id;

        @JsonProperty("discount")  // 프로모션 할인 금액
        private final int discount;

        public Promotion(String id, int discount) {
            this.id = id;
            this.discount = discount;
        }
    }
}