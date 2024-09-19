package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberIdAndNickDto {
    @Schema(description = "회원 식별값")
    private Long id;
    @Schema(description = "닉네임")
    private String nick;

    public MemberIdAndNickDto(Long id, String nick) {
        this.id = id;
        this.nick = nick;
    }
}
