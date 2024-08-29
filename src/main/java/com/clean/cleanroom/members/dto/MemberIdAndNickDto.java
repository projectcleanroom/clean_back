package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MemberIdAndNickDto {
    private Long id;
    private String nick;

    public MemberIdAndNickDto(Long id, String nick) {
        this.id = id;
        this.nick = nick;
    }
}
