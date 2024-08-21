package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.partner.entity.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateDetailResponseDto {

    private Long id;
    private int price;
    private LocalDateTime fixedDate;
    private String statement;
    private StatusType status;

    public EstimateDetailResponseDto (Estimate estimate) {
        this.id = estimate.getId();
        this.price = estimate.getPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.status = estimate.getStatus();
    }
}
