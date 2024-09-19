package com.clean.cleanroom.commission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommissionFileResponseDto {
    @Schema(description = "파일 이름")
    private String fileName;
    private String message;

    // 생성자
    public CommissionFileResponseDto(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}

