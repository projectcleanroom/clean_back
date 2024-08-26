package com.clean.cleanroom.members.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembersLoginRequestDto {
    @Email
    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@$%^&*_])[a-zA-Z\\d~!@$%^&*_]{8,}$",
            message = "비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수 문자(~!@$%^&*_)를 포함해야 합니다.")
    private String password;
}
