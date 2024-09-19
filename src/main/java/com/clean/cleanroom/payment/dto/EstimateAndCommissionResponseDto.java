package com.clean.cleanroom.payment.dto;

import com.clean.cleanroom.enums.CleanType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EstimateAndCommissionResponseDto {

    @JsonProperty("estimate_id")  // 견적 ID
    private final Long estimateId;

    @JsonProperty("commission_id")  // 의뢰 ID
    private final Long commissionId;

    @JsonProperty("estimate_amount")  // 견적 금액
    private final int estimateAmount;

    @JsonProperty("member_nick")  // 멤버 닉네임
    private final String memberNick;

    @JsonProperty("member_phone_number")  // 멤버 전화번호
    private final String memberPhoneNumber;

    @JsonProperty("member_email")  // 멤버 이메일
    private final String memberEmail;

    @JsonProperty("clean_type")  // 클린 타입
    private final CleanType cleanType;

    public EstimateAndCommissionResponseDto(Long estimateId, Long commissionId, int estimateAmount,
                                            String memberNick, String memberPhoneNumber, String memberEmail, CleanType cleanType) {
        this.estimateId = estimateId;
        this.commissionId = commissionId;
        this.estimateAmount = estimateAmount;
        this.memberNick = memberNick;
        this.memberPhoneNumber = memberPhoneNumber;
        this.memberEmail = memberEmail;
        this.cleanType = cleanType;
    }

}