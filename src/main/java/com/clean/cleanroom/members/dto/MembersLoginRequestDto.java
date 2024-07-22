package com.clean.cleanroom.members.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersLoginRequestDto {
    private String email;
    private String password;
}