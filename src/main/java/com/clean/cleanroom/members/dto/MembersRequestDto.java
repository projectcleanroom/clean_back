package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersRequestDto {
    private String email;
    private String password;
    private String nick;
    private String phoneNumber;
}
