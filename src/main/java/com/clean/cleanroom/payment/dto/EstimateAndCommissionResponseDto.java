package com.clean.cleanroom.payment.dto;

import lombok.Getter;

@Getter
public class EstimateAndCommissionResponseDto {

    private final Long estimateId;  // 견적 ID
    private final Long commissionId;  // 의뢰 ID
    private final int estimateAmount;  // 견적 금액
    private final String memberNick;  // 멤버 닉네임
    private final String memberPhoneNumber;  // 멤버 전화번호
    private final String memberEmail;  // 멤버 이메일

    public EstimateAndCommissionResponseDto(Long estimateId, Long commissionId, int estimateAmount,
                                            String memberNick, String memberPhoneNumber, String memberEmail) {
        this.estimateId = estimateId;
        this.commissionId = commissionId;
        this.estimateAmount = estimateAmount;
        this.memberNick = memberNick;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberEmail = memberEmail;
    }
}