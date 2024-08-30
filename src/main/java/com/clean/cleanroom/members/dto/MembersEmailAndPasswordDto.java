package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersEmailAndPasswordDto {

    private String email;
    private String password;


    // JPQL에서 사용될 생성자
    public MembersEmailAndPasswordDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}