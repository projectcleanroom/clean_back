package com.clean.cleanroom.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoRequestDto {
    private String kakaoId;
    private String email;
    private String nick;
    private String phoneNumber;  // 선택적 필드

    public KakaoUserInfoRequestDto(String kakaoId, String email, String nick) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nick = nick;

    }
}