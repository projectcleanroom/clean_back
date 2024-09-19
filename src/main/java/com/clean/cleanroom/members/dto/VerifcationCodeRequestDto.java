package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class VerifcationCodeRequestDto {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "인증 코드")
    private String code;
}