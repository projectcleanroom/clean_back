package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import lombok.Getter;

@Getter
public class MembersAddressResponseDto {
    private Long id;
    private String address;

    public MembersAddressResponseDto(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
    }
}
