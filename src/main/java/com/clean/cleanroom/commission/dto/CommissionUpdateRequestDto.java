package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommissionUpdateRequestDto {

    private String image;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate;
    private String significant;
    private StatusType status;


    public CommissionUpdateRequestDto(String image, int size, HouseType houseType, CleanType cleanType, LocalDateTime desiredDate, String significant, StatusType status) {

        this.image = image;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.desiredDate = desiredDate;
        this.significant = significant;
        this.status = status;
    }

}
