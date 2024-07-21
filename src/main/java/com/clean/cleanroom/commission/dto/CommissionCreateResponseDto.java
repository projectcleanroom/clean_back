package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;

import java.time.LocalDateTime;

public class CommissionCreateResponseDto {

    private Long commissionId;
    private Members members;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Address address;
    private LocalDateTime desiredDate;
    private String significant;


    public CommissionCreateResponseDto(Commission commission) {
        this.commissionId = commission.getId();
        this.members = commission.getMembers();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.address = commission.getAddress();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
    }
}
