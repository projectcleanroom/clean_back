//package com.clean.cleanroom.payment.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//
//@Getter
//public class PortOneTokenResponseDto {
//
//    @JsonProperty("code")  // 응답 코드
//    private final int code;
//
//    @JsonProperty("message")  // 응답 메시지
//    private final String message;
//
//    @JsonProperty("response")  // 토큰 정보
//    private final AccessToken response;
//
//    public PortOneTokenResponseDto(int code, String message, AccessToken response) {
//        this.code = code;
//        this.message = message;
//        this.response = response;
//    }
//
//    @Getter
//    public static class AccessToken {
//
//        @JsonProperty("access_token")  // 액세스 토큰
//        private final String accessToken;
//
//        @JsonProperty("expired_at")  // 만료 시간 (Unix timestamp)
//        private final long expiredAt;
//
//        @JsonProperty("now")  // 현재 시간 (Unix timestamp)
//        private final long now;
//
//        public AccessToken(String accessToken, long expiredAt, long now) {
//            this.accessToken = accessToken;
//            this.expiredAt = expiredAt;
//            this.now = now;
//        }
//    }
//}
package com.clean.cleanroom.payment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter

public class PortOneTokenResponseDto {

    private final int code;  // 응답 코드
    private final String message;  // 응답 메시지
    private final AccessToken response;  // 토큰 정보

    // 기본 생성자
    public PortOneTokenResponseDto() {
        this.code = 0;
        this.message = null;
        this.response = null;
    }

    // JSON Creator로 설정
    @JsonCreator
    public PortOneTokenResponseDto(
            @JsonProperty("code") int code,
            @JsonProperty("message") String message,
            @JsonProperty("response") AccessToken response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    @Getter

    public static class AccessToken {

        private final String accessToken;  // 액세스 토큰
        private final long expiredAt;  // 만료 시간 (Unix timestamp)
        private final long now;  // 현재 시간 (Unix timestamp)

        // 기본 생성자
        public AccessToken() {
            this.accessToken = null;
            this.expiredAt = 0;
            this.now = 0;
        }

        // JSON Creator로 설정
        @JsonCreator
        public AccessToken(
                @JsonProperty("access_token") String accessToken,
                @JsonProperty("expired_at") long expiredAt,
                @JsonProperty("now") long now) {
            this.accessToken = accessToken;
            this.expiredAt = expiredAt;
            this.now = now;
        }
    }
}