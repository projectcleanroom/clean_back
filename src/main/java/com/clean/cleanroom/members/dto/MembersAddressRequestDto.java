package com.clean.cleanroom.members.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MembersAddressRequestDto {
    @NotEmpty(message = "주소는 필수 입력 항목입니다.")
    private String address;
}
