package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OAuthTokenDto {
    @Schema(description = "엑세스 토큰")
    private String access_token;
    @Schema(description = "토큰 타입")
    private String token_type;
    @Schema(description = "리프레쉬 토큰")
    private String refresh_token;
    @Schema(description = "카카오 서버에서 받아올 데이터 범위")
    private String scope;
}