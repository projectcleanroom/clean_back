package com.clean.cleanroom.members.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
public class MembersAddressRequestDto {
    @Comment("주소1")
    @NotEmpty(message = "주소는 필수 입력 항목입니다.")
    private String address;

    @Comment("주소2")
    private String addressDetail;

    @Comment("우편번호")
    private String addressCode;

}
