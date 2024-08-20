package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MyCommissionResponseDto {

    private Long commissionId;
    private String memberNick;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private Long addressId;
    private LocalDateTime desiredDate;
    private String significant;
    private String image;
    private StatusType status;


    public MyCommissionResponseDto(Commission commission) {
        this.commissionId = commission.getId();
        this.memberNick = commission.getMembers().getNick();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.addressId = commission.getAddress().getId();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
        this.image = commission.getImage();
        this.status = commission.getStatus();
    }

}
