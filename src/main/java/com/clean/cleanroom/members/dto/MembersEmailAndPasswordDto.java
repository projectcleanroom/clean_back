package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersEmailAndPasswordDto {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;


    // JPQL에서 사용될 생성자
    public MembersEmailAndPasswordDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}