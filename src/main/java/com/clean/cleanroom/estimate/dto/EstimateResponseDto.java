package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.service.EstimateService;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EstimateResponseDto {

    private Long Id;

    private Long commissionId;

    private int price;

    private LocalDateTime fixedDate;

    private String statement;



    public  EstimateResponseDto(Estimate estimate) {
        this.Id = estimate.getId();
        this.commissionId = estimate.getCommissionId().getId();
        this.fixedDate = estimate.getFixedDate();
        this.price = estimate.getPrice();
        this.statement = estimate.getStatement();
    }
}
