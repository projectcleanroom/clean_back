package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.partner.entity.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EstimateDetailResponseDto {

    private Long id; // 견적
    private Long commissionId; // 의뢰
    private Long partnerId; // 파트너

    private int price; // 확정 가격
    private LocalDateTime fixedDate; // 확정 날짜
    private String statement; // 코멘트
    private StatusType status; // 매칭 상태

    private CleanType cleanType; // 청소 타입
    private int size; // 평수
    private LocalDateTime desiredDate; // 희망 날짜
    private String significant; // 요청사항
    private StatusType commissionStatus; // 의뢰 매칭 상태

    private String phoneNumber; // 파트너 폰번호
    private String managerName; // 담당자 이름
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
