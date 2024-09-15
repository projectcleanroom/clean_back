package com.clean.cleanroom.payment.dto;

import lombok.Getter;


@Getter
public class PaymentDetailRequestDto {

    private final String imp_uid;  // Port One의 고유 거래 ID

    public PaymentDetailRequestDto(String imp_uid) {
        this.imp_uid = imp_uid;
    }
}