package com.clean.cleanroom.payment.dto;

import com.clean.cleanroom.enums.CleanType;
import lombok.Getter;

@Getter
public class EstimateAndCommissionResponseDto {

    private final Long estimate_id;  // 견적 ID
    private final Long commission_id;  // 의뢰 ID
    private final int estimate_amount;  // 견적 금액
    private final String member_nick;  // 멤버 닉네임
    private final String member_phone_number;  // 멤버 전화번호
    private final String member_email;  // 멤버 이메일
    private final CleanType cleanType;

    public EstimateAndCommissionResponseDto(Long estimate_id, Long commission_id, int estimate_amount,
                                            String member_nick, String member_phone_number, String member_email, CleanType cleanType) {
        this.estimate_id = estimate_id;
        this.commission_id = commission_id;
        this.estimate_amount = estimate_amount;
        this.member_nick = member_nick;
        this.member_phone_number = member_phone_number;
        this.member_email = member_email;
        this.cleanType = cleanType;
    }

}