package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PortOneTokenResponseDto {

    @JsonProperty("code")  // 응답 코드
    private final int code;

    @JsonProperty("message")  // 응답 메시지
    private final String message;

    @JsonProperty("response")  // 토큰 정보
    private final AccessToken response;

    public PortOneTokenResponseDto(int code, String message, AccessToken response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class AccessToken {

        @JsonProperty("token")  // 액세스 토큰
        private final String token;

        @JsonProperty("expired_at")  // 만료 시간 (Unix timestamp)
        private final long expiredAt;

        @JsonProperty("now")  // 현재 시간 (Unix timestamp)
        private final long now;

        public AccessToken(String token, long expiredAt, long now) {
            this.token = token;
            this.expiredAt = expiredAt;
            this.now = now;
        }
    }
}
