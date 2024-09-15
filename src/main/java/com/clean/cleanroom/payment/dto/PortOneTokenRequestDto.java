package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PortOneTokenRequestDto {

    @JsonProperty("imp_key")  // Port One의 인증 키
    private final String impKey;

    @JsonProperty("imp_secret")  // Port One의 인증 시크릿
    private final String impSecret;

    public PortOneTokenRequestDto(String impKey, String impSecret) {
        this.impKey = impKey;
        this.impSecret = impSecret;
    }
}