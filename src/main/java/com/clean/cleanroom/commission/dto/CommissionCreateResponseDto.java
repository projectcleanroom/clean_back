package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommissionCreateResponseDto {

    private String message;

    public CommissionCreateResponseDto() {
        this.message = "청소의뢰 생성 완료";
    }
}
