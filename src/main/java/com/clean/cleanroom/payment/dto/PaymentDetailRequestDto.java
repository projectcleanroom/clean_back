package com.clean.cleanroom.payment.dto;

import lombok.Getter;


@Getter
public class PaymentDetailRequestDto {

    private final String impUid;  // Port One의 고유 거래 ID

    public PaymentDetailRequestDto(String impUid) {
        this.impUid = impUid;
    }
}