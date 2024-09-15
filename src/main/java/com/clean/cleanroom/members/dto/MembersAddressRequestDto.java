package com.clean.cleanroom.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
public class MembersAddressRequestDto {
    @Comment("Address ID")
    @Schema(description = "주소 식별값")
    private Long id;

    @Comment("주소1")
    @NotEmpty(message = "주소는 필수 입력 항목입니다.")
    @Schema(description = "주소")
    private String address;

    @Comment("주소2")
    @Schema(description = "상세 주소")
    private String addressDetail;

    @Comment("우편번호")
    @Schema(description = "우편 번호")
    private String addressCode;

}
