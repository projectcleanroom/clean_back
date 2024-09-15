package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class PortOneTokenResponseDto {

    private final int code;  // 응답 코드
    private final String message;  // 응답 메시지
    private final  AccessToken response;  // 토큰 정보

    public PortOneTokenResponseDto(int code, String message, AccessToken response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter
    public static class AccessToken {
        private final String token;  // 액세스 토큰
        private final long expiredAt;  // 만료 시간 (Unix timestamp)
        private final long now;

        public AccessToken(String token, long expiredAt, long now) {
            this.token = token;
            this.expiredAt = expiredAt;
            this.now = now;
        }


        public String getToken() {
            return token;
        }

        public long getExpiredAt() {
            return expiredAt;
        }

        public long getNow() {
            return now;
        }
    }
}