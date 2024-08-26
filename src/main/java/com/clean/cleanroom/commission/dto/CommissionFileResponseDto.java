package com.clean.cleanroom.commission.dto;

import lombok.Getter;

@Getter
public class CommissionFileResponseDto {
    private String fileName;
    private String message;

    // 생성자
    public CommissionFileResponseDto(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}

