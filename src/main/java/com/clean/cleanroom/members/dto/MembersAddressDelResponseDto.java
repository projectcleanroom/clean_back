package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersAddressDelResponseDto {
    @Schema(description = "삭제가 완료 되었습니다.")
    private String msg;

    public MembersAddressDelResponseDto() {
        this.msg = "삭제가 완료 되었습니다";
    }
}
