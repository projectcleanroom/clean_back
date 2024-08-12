package com.clean.cleanroom.members.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MembersUpdateProfileRequestDto {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@$%^&*_])[a-zA-Z\\d~!@$%^&*_]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수 문자(~!@$%^&*_)를 포함해야 합니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣_-]{1,15}$",
            message = "닉네임은 숫자, 대문자, 소문자, 한글, 밑줄(_), 대시(-)만 포함할 수 있으며, 길이는 1자에서 15자 사이여야 합니다.")
    private String nick;

    @Pattern(regexp = "^01\\d{9}$",
            message = "전화번호는 01012345678 형식이어야 합니다.")
    private String phoneNumber;
}
