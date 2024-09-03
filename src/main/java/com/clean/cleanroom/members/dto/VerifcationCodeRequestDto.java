package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class VerifcationCodeRequestDto {
    private String email;
    private String code;
}