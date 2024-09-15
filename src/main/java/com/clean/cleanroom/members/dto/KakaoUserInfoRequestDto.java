package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoRequestDto {
    @Schema(description = "카카오 식별값")
    private String kakaoId;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "닉네임")
    private String nick;
    @Schema(description = "휴대폰 번호")
    private String phoneNumber;  // 선택적 필드

    public KakaoUserInfoRequestDto(String kakaoId, String email, String nick) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nick = nick;

    }
}