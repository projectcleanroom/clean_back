package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionConfirmDetailResponseDto {
    private Long commissionId;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Long addressId;
    private LocalDateTime desiredDate;
    private String significant;
    private StatusType status;
    private StatusType estimatedStatus;
    private int tmpPrice;
    private int Price;
    private String statment;
    private LocalDateTime fixedDate;
    private String image;



    public CommissionConfirmDetailResponseDto(Commission commission, Estimate estimate, Address address) {
        this.commissionId = commission.getId();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.addressId = address.getId();
        this.desiredDate = getDesiredDate();
        this.significant = getSignificant();
        this.status = getStatus();
        this.estimatedStatus = estimate.getStatus();
        this.tmpPrice = estimate.getTmpPrice();
        this.Price = estimate.getPrice();
        this.statment = estimate.getStatement();
        this.fixedDate = estimate.getFixedDate();
        this.image = commission.getImage();
    }
}
