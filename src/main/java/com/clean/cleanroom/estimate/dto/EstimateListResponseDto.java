package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateListResponseDto {
    @Schema(description = "견정 식별값")
    private Long id;
    @Schema(description = "청소 의뢰 식별값")
    private Long commissionId;
    @Schema(description = "확정 가격")
    private int price;
    @Schema(description = "확정 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate;
    @Schema(description = "코멘트")
    private String statement;

    public  EstimateListResponseDto(Estimate estimate) {
        this.id = estimate.getId();
        this.commissionId = estimate.getCommission().getId();
        this.fixedDate = estimate.getFixedDate();
        this.price = estimate.getPrice();
        this.statement = estimate.getStatement();
    }

}
