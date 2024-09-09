package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommissionConfirmDetailResponseDto {
    @Schema(description = "청소 의뢰 식별값")
    private Long commissionId;
    @Schema(description = "닉네임")
    private String memberNick;
    @Schema(description = "평수")
    private int size;
    @Schema(description = "주거 형태")
    private HouseType houseType;
    @Schema(description = "청소 타입")
    private CleanType cleanType;
    @Schema(description = "주소 식별값")
    private Long addressId;
    @Schema(description = "희망 일짜", example = "2024-09-05T10:00:00")
    private LocalDateTime desiredDate;
    @Schema(description = "특이 사항")
    private String significant;
    @Schema(description = "의뢰 상태")
    private StatusType status;
    @Schema(description = "이미지")
    private String image;


    public CommissionConfirmDetailResponseDto(Commission commission) {
        this.commissionId = commission.getId();
        this.memberNick = commission.getMembers().getNick();
        this.size = commission.getSize();
        this.houseType = commission.getHouseType();
        this.cleanType = commission.getCleanType();
        this.addressId = commission.getAddress().getId();
        this.desiredDate = commission.getDesiredDate();
        this.significant = commission.getSignificant();
        this.status = commission.getStatus();
        this.image = commission.getImage();
    }
}
