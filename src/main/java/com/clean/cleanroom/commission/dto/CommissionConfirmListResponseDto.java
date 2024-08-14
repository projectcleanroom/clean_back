package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommissionConfirmListResponseDto {

    private Long id;
    private int size;
    private HouseType houseType;
    private CleanType cleanType;
    private LocalDateTime desiredDate;
    private String significant;
    private String image;

    private List<EstimateResponseDto> estimates;


    // 생성자 추가
    public CommissionConfirmListResponseDto(Long id, int size, HouseType houseType, CleanType cleanType,
                                            LocalDateTime desiredDate, String significant,
                                            List<EstimateResponseDto> estimates, String image) {
        this.id = id;
        this.size = size;
        this.houseType = houseType;
        this.cleanType = cleanType;
        this.desiredDate = desiredDate;
        this.significant = significant;
        this.estimates = estimates;
        this.image = image;
    }
}
