package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class PortOneTokenRequestDto {
    private final String imp_key;
    private final String imp_secret;

    public PortOneTokenRequestDto(String imp_key, String imp_secret) {
        this.imp_key = imp_key;
        this.imp_secret = imp_secret;
    }
}