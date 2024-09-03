package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommissionConfirmListResponseDto {

    private Long commissionId;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate;
    private String significant;
    private String image;
    private StatusType status;
    private Long addressId;

    private List<EstimateResponseDto> estimates;




    public CommissionConfirmListResponseDto(Commission commission, List<EstimateResponseDto> estimateDtos) {
        this.commissionId = commission.getId();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
        this.image = commission.getImage();
        this.status = commission.getStatus();
        this.addressId = commission.getAddress().getId();
        this.estimates = estimateDtos;
    }
}
