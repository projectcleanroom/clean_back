package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionUpdateRequestDto {

    private Long commissionId;
    private Long memberId;
    private Long addressId;
    private String image;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate;
    private String significant;


    public CommissionUpdateRequestDto(Long commissionId, Long memberId, Long addressId, String image, int size, HouseType houseType, CleanType cleanType, LocalDateTime desiredDate, String significant) {
        this.commissionId = commissionId;
        this.memberId = memberId;
        this.addressId = addressId;
        this.image = image;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.desiredDate = desiredDate;
        this.significant = significant;
    }

}
