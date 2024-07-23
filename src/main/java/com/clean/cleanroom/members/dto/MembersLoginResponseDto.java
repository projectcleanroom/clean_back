package com.clean.cleanroom.members.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembersLoginResponseDto {
    private String email;
    private String nick;
    private String message;

    public MembersLoginResponseDto(String email, String nick) {
        this.email = email;
        this.nick = nick;
    }
    public MembersLoginResponseDto(String message) {
        this.message = message;
    }
}