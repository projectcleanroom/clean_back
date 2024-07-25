package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;

@Getter
public class MembersResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nick;
    private String phoneNumber;

}
