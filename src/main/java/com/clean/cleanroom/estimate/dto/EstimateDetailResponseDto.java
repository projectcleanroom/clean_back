package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.partner.entity.Partner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateDetailResponseDto {
    @Schema(description = "견적 식별값")
    private Long id; // 견적
    @Schema(description = "청소 의뢰 식별값")
    private Long commissionId; // 의뢰
    @Schema(description = "파트너 식별값")
    private Long partnerId; // 파트너

    @Schema(description = "확정 가격")
    private int price; // 확정 가격

    @Schema(description = "확정 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime fixedDate; // 확정 날짜
    @Schema(description = "코멘트")
    private String statement; // 코멘트
    @Schema(description = "견적 상태")
    private StatusType status; // 매칭 상태

    @Schema(description = "청소 타입")
    private CleanType cleanType; // 청소 타입
    @Schema(description = "평수")
    private int size; // 평수
    @Schema(description = "희망 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate; // 희망 날짜
    @Schema(description = "요청 사항")
    private String significant; // 요청사항
    @Schema(description = "의뢰 상태")
    private StatusType commissionStatus; // 의뢰 매칭 상태
    @Schema(description = "파트너 폰번호")
    private String phoneNumber; // 파트너 폰번호
    @Schema(description = "담당자 이름")
    private String managerName; // 담당자 이름
    @Schema(description = "업체명")
    private String companyName; // 업체명


    public EstimateDetailResponseDto (Estimate estimate,
                                      Commission commission,
                                      Partner partner) {
        this.id = estimate.getId();
        this.commissionId = commission.getId();
        this.partnerId = partner.getId();
        this.price = estimate.getPrice();
        this.fixedDate = estimate.getFixedDate();
        this.statement = estimate.getStatement();
        this.status = estimate.getStatus();
        this.cleanType = commission.getCleanType();
        this.size = commission.getSize();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
        this.commissionStatus = commission.getStatus();
        this.phoneNumber = partner .getPhoneNumber();
        this.managerName = partner .getManagerName();
        this.companyName = partner .getCompanyName();
    }
}
