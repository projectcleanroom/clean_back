package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersProfileResponseDto {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "닉네임")
    private String nick;
    @Schema(description = "휴대폰 번호")
    private String phoneNumber;

    public MembersProfileResponseDto(Members members) {
        this.email = members.getEmail();
        this.nick = members.getNick();
        this.phoneNumber = members.getPhoneNumber();
    }
}
