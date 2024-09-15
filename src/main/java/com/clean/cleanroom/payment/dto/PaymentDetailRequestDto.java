package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PaymentDetailRequestDto {

    @JsonProperty("imp_uid")  // Port One의 고유 거래 ID
    private final String impUid;

    public PaymentDetailRequestDto(String impUid) {
        this.impUid = impUid;
    }
}