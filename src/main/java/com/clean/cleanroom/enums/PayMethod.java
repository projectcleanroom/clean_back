package com.clean.cleanroom.enums;

public enum PayMethod {
    CARD, // 신용카드
    TRANSFER, // 실시간 계좌이체
    VIRTUAL_ACCOUNT, // 가상계좌
    MOBILE, // 휴대폰 소액결제
    EASY_PAY; // 간편결제

    // 문자열을 Pay_method enum으로 변환하는 메서드
    public static PayMethod fromString(String method) {
        switch (method.toUpperCase()) {
            case "CARD":
                return CARD;
            case "TRANSFER":
                return TRANSFER;
            case "VIRTUAL_ACCOUNT":
                return VIRTUAL_ACCOUNT;
            case "MOBILE":
                return MOBILE;
            case "EASY_PAY":
                return EASY_PAY;
            default:
                throw new IllegalArgumentException("Unknown payment method: " + method);
        }
    }
}

