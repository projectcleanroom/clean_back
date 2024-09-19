package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersLoginResponseDto {
    @Schema(description = "로그인 성공!")
    private String message;

    public MembersLoginResponseDto(String message) {
        this.message = message;
    }
}
