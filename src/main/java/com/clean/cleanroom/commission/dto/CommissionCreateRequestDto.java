package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionCreateRequestDto {
    @Schema(description = "이미지")
    private String image;
    @Schema(description = "평수")
    private int size;
    @Schema(description = "주거 형태")
    private HouseType houseType;
    @Schema(description = "청소 타입")
    private CleanType cleanType;
    @Schema(description = "주소 식별값")
    private Long addressId;
    @Schema(description = "희망 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate;
    @Schema(description = "특이 사항")
    private String significant;

}
