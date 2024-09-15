package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionCancelResponseDto {
    @Schema(description = "청소의뢰 취소 완료")
    private String message;

    public CommissionCancelResponseDto() {
        this.message = "청소의뢰 취소 완료";
    }

}
