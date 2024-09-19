package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommissionConfirmListResponseDto {
    @Schema(description = "청소 의뢰 식별값")
    private Long commissionId;
    @Schema(description = "평수")
    private int size;
    @Schema(description = "주거 형태")
    private HouseType houseType;
    @Schema(description = "청소 타입")
    private CleanType cleanType;
    @Schema(description = "희망 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate;
    @Schema(description = "특이 사항")
    private String significant;
    @Schema(description = "이미지")
    private String image;
    @Schema(description = "의뢰 상태")
    private StatusType status;
    @Schema(description = "주소 식별값")
    private Long addressId;
    @Schema(description = "견적 리스트")
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
