package com.clean.cleanroom.payment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CancelPaymentResponseDto {

    private final int code;  // 응답 코드
    private final String message;  // 응답 메시지
    private final CancelPaymentResponseDetailDto response;  // 응답 상세

    public CancelPaymentResponseDto(int code, String message, CancelPaymentResponseDetailDto response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class CancelPaymentResponseDetailDto {

        private final String imp_uid;  // 결제 ID
        private final String merchant_uid;  // 주문 ID
        private final String pay_method;  // 결제 방법
        private final String channel;  // 결제 채널
        private final String pg_provider;  // PG사 제공자
        private final String emb_pg_provider;  // 임베디드 PG 제공자
        private final String pg_tid;  // PG 트랜잭션 ID
        private final String pg_id;  // PG ID
        private final boolean escrow;  // 에스크로 여부
        private final String apply_num;  // 카드 승인 번호
        private final String bank_code;  // 은행 코드
        private final String bank_name;  // 은행 이름
        private final String card_code;  // 카드 코드
        private final String card_name;  // 카드사 이름
        private final String card_issuer_code;  // 카드 발급사 코드
        private final String card_issuer_name;  // 카드 발급사 이름
        private final String card_publisher_code;  // 카드 발행사 코드
        private final String card_publisher_name;  // 카드 발행사 이름
        private final int card_quota;  // 할부 개월 수
        private final String card_number;  // 카드 번호 (일부 마스킹)
        private final int card_type;  // 카드 타입
        private final String vbank_code;  // 가상 계좌 코드
        private final String vbank_name;  // 가상 계좌 이름
        private final String vbank_num;  // 가상 계좌 번호
        private final String vbank_holder;  // 가상 계좌 예금주
        private final long vbank_date;  // 가상 계좌 만료일
        private final long vbank_issued_at;  // 가상 계좌 발급일
        private final String name;  // 결제 상품명
        private final int amount;  // 결제 금액
        private final int cancel_amount;  // 취소 금액
        private final String currency;  // 통화
        private final String buyer_name;  // 구매자 이름
        private final String buyer_email;  // 구매자 이메일
        private final String buyer_tel;  // 구매자 전화번호
        private final String buyer_addr;  // 구매자 주소
        private final String buyer_postcode;  // 구매자 우편번호
        private final String custom_data;  // 사용자 정의 데이터
        private final String user_agent;  // 사용자 에이전트
        private final String status;  // 결제 상태
        private final long started_at;  // 결제 시작 시간
        private final long paid_at;  // 결제 완료 시간
        private final long failed_at;  // 결제 실패 시간
        private final long cancelled_at;  // 결제 취소 시간
        private final String fail_reason;  // 실패 사유
        private final String cancel_reason;  // 취소 사유
        private final String receipt_url;  // 영수증 URL
        private final List<CancelHistory> cancel_history;  // 취소 내역
        private final List<String> cancel_receipt_urls;  // 취소 영수증 URL 목록
        private final boolean cash_receipt_issued;  // 현금 영수증 발행 여부
        private final String customer_uid;  // 고객 UID
        private final String customer_uid_usage;  // 고객 UID 사용 방식
        private final Promotion promotion;  // 프로모션 정보

        public CancelPaymentResponseDetailDto(String imp_uid, String merchant_uid, String pay_method, String channel, String pg_provider,
                                              String emb_pg_provider, String pg_tid, String pg_id, boolean escrow, String apply_num, String bank_code,
                                              String bank_name, String card_code, String card_name, String card_issuer_code, String card_issuer_name,
                                              String card_publisher_code, String card_publisher_name, int card_quota, String card_number, int card_type,
                                              String vbank_code, String vbank_name, String vbank_num, String vbank_holder, long vbank_date, long vbank_issued_at,
                                              String name, int amount, int cancel_amount, String currency, String buyer_name, String buyer_email,
                                              String buyer_tel, String buyer_addr, String buyer_postcode, String custom_data, String user_agent, String status,
                                              long started_at, long paid_at, long failed_at, long cancelled_at, String fail_reason, String cancel_reason,
                                              String receipt_url, List<CancelHistory> cancel_history, List<String> cancel_receipt_urls, boolean cash_receipt_issued,
                                              String customer_uid, String customer_uid_usage, Promotion promotion) {
            this.imp_uid = imp_uid;
            this.merchant_uid = merchant_uid;
            this.pay_method = pay_method;
            this.channel = channel;
            this.pg_provider = pg_provider;
            this.emb_pg_provider = emb_pg_provider;
            this.pg_tid = pg_tid;
            this.pg_id = pg_id;
            this.escrow = escrow;
            this.apply_num = apply_num;
            this.bank_code = bank_code;
            this.bank_name = bank_name;
            this.card_code = card_code;
            this.card_name = card_name;
            this.card_issuer_code = card_issuer_code;
            this.card_issuer_name = card_issuer_name;
            this.card_publisher_code = card_publisher_code;
            this.card_publisher_name = card_publisher_name;
            this.card_quota = card_quota;
            this.card_number = card_number;
            this.card_type = card_type;
            this.vbank_code = vbank_code;
            this.vbank_name = vbank_name;
            this.vbank_num = vbank_num;
            this.vbank_holder = vbank_holder;
            this.vbank_date = vbank_date;
            this.vbank_issued_at = vbank_issued_at;
            this.name = name;
            this.amount = amount;
            this.cancel_amount = cancel_amount;
            this.currency = currency;
            this.buyer_name = buyer_name;
            this.buyer_email = buyer_email;
            this.buyer_tel = buyer_tel;
            this.buyer_addr = buyer_addr;
            this.buyer_postcode = buyer_postcode;
            this.custom_data = custom_data;
            this.user_agent = user_agent;
            this.status = status;
            this.started_at = started_at;
            this.paid_at = paid_at;
            this.failed_at = failed_at;
            this.cancelled_at = cancelled_at;
            this.fail_reason = fail_reason;
            this.cancel_reason = cancel_reason;
            this.receipt_url = receipt_url;
            this.cancel_history = cancel_history;
            this.cancel_receipt_urls = cancel_receipt_urls;
            this.cash_receipt_issued = cash_receipt_issued;
            this.customer_uid = customer_uid;
            this.customer_uid_usage = customer_uid_usage;
            this.promotion = promotion;
        }

        @Getter
        public static class CancelHistory {
            private final String pg_tid;
            private final int amount;
            private final long cancelled_at;
            private final String reason;
            private final String cancellation_id;
            private final String receipt_url;

            public CancelHistory(String pg_tid, int amount, long cancelled_at, String reason, String cancellation_id, String receipt_url) {
                this.pg_tid = pg_tid;
                this.amount = amount;
                this.cancelled_at = cancelled_at;
                this.reason = reason;
                this.cancellation_id = cancellation_id;
                this.receipt_url = receipt_url;
            }
        }

        @Getter
        public static class Promotion {
            private final String id;
            private final int discount;

            public Promotion(String id, int discount) {
                this.id = id;
                this.discount = discount;
            }
        }
    }
}