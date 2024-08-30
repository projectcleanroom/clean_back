package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersLoginResponseDto {
    private String message;

    public MembersLoginResponseDto(String message) {
        this.message = message;
    }
}