package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MembersAddressResponseDto {
    @Schema(description = "주소 식별값")
    private Long id;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "상세 주소")
    private String addressDetail;
    @Schema(description = "우편 번호")
    private String addressCode;

    public MembersAddressResponseDto(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.addressDetail = address.getAddressDetail();
        this.addressCode = address.getAddressCode();
    }
}
