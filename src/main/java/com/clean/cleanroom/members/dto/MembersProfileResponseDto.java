package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersProfileResponseDto {
    private String email;
    private String nick;
    private String phoneNumber;

    public MembersProfileResponseDto(String email, String nick, String phoneNumber) {
        this.email = email;
        this.nick = nick;
        this.phoneNumber = phoneNumber;
    }
}
