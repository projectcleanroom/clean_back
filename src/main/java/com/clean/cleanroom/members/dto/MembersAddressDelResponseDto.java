package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class MembersAddressDelResponseDto {
    private String msg;

    public MembersAddressDelResponseDto() {
        this.msg = "삭제가 완료 되었습니다";
    }
}
