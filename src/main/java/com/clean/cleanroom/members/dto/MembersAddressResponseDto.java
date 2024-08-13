package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;

@Getter
public class MembersAddressResponseDto {
    private Long id;
    private String address;
    private String addressDetail;
    private String addressCode;

    public MembersAddressResponseDto(Address address) {
        this.address = address.getAddress();
        this.addressDetail = address.getAddressDetail();
        this.addressCode = address.getAddressCode();
    }
}
