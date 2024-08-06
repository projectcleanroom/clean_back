package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;

@Getter
public class MembersGetProfileResponseDto {
    private String email;
    private String nick;
    private String phoneNumber;

    public MembersGetProfileResponseDto(Members members) {
        this.email = members.getEmail();
        this.nick = members.getNick();
        this.phoneNumber = members.getPhoneNumber();
    }
}
