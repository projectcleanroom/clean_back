package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.entity.Address;
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

}
