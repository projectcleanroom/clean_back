package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionCreateRequestDto {

    private Long membersId;
    private String image;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Long addressId;
    private LocalDateTime desiredDate;
    private String significant;


    //테스트 전용 생성자
    public CommissionCreateRequestDto(Long membersId, String image, int size, HouseType houseType, CleanType cleanType, Long addressId, LocalDateTime desiredDate, String significant) {
        this.membersId = membersId;
        this.image = image;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.addressId = addressId;
        this.desiredDate = desiredDate;
        this.significant = significant;
    }

}
