package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersAddressDelResponseDto;
import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import com.clean.cleanroom.members.dto.MembersGetProfileResponseDto;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembersAddressService {
    private final AddressRepository addressRepository;
    private final JwtUtil jwtUtil;

    public MembersAddressService(AddressRepository addressRepository, JwtUtil jwtUtil) {
        this.addressRepository = addressRepository;
        this.jwtUtil = jwtUtil;
    }
    public MembersAddressResponseDto createAddress(String token, MembersAddressRequestDto requestDto) {
        String email = jwtUtil.extractEmail(token);
        Address address = new Address(email,requestDto);
        addressRepository.save(address);
        return new MembersAddressResponseDto(address);
    }

    public List<MembersAddressResponseDto> getAddress(String token) {
        String email = jwtUtil.extractEmail(token);
        List<Address> address = addressRepository.findAllByEmail(email);

        List<MembersAddressResponseDto> addressResponseDtos = new ArrayList<>();
        for (Address a : address) {
            addressResponseDtos.add(new MembersAddressResponseDto(a));
        }
        return addressResponseDtos;
    }

    public MembersAddressDelResponseDto delAddress(String token, Long id) {
        String email = jwtUtil.extractEmail(token);
        Address address = addressRepository.findByEmailAndId(email,id).orElseThrow(
                ()-> new CustomException(ErrorMsg.ADDRESS_NOT_FOUND)
        );
        addressRepository.delete(address);
        return new MembersAddressDelResponseDto();
    }
}
