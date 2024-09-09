package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class KakaoAuthCodeRequestDto {
    @Schema(description = "인증 코드")
    private String code;
}