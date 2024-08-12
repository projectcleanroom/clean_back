package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionCancelResponseDto {

    private Long commissionId;
    private String memberNick;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Long addressId;
    private LocalDateTime desiredDate;
    private String significant;

    public CommissionCancelResponseDto(Commission commission) {
        this.commissionId = commission.getId();
        this.memberNick = commission.getMembers().getNick();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.addressId = commission.getAddress().getId();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
    }

}
