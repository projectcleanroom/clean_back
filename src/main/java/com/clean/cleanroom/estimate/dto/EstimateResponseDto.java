package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateResponseDto {


    private Long id;
    private int price;
    private LocalDateTime fixedDate;
    private String statement;
    private boolean approved;
    private StatusType status;

    // 필요한 추가 정보들
    private Long partnerId;
    private String partnerName;

    public EstimateResponseDto(Estimate estimate) {
        this.id = estimate.getId();
        this.price = estimate.getPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.approved = estimate.isApproved();
        this.partnerId = estimate.getPartner().getId();
        this.partnerName = estimate.getPartner().getCompanyName();
        this.status = estimate.getStatus();
    }
}

