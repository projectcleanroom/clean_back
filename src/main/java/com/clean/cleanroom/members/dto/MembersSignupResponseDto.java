package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersSignupResponseDto {
    @Schema(description = "회원 가입 성공!")
    private String message;

    public MembersSignupResponseDto(Members members) {
        this.message = "회원 가입 성공!";
    }

}
