package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateResponseDto {

    @Schema(description = "견적 식별값")
    private Long id;
    @Schema(description = "확정 가격")
    private int price;
    @Schema(description = "확정 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate;
    @Schema(description = "코멘트")
    private String statement;
    @Schema(description = "견적 승인")
    private boolean approved;
    @Schema(description = "견적 상태")
    private StatusType status;

    // 필요한 추가 정보들
    @Schema(description = "파트너 식별값")
    private Long partnerId;
    @Schema(description = "파트너 이름")
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

