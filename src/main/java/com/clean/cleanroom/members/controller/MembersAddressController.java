package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersAddressDelResponseDto;
import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import com.clean.cleanroom.members.dto.MembersGetProfileResponseDto;
import com.clean.cleanroom.members.service.MembersAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class MembersAddressController {
    private final MembersAddressService membersAddressService;

    public MembersAddressController (MembersAddressService membersAddressService){
        this.membersAddressService = membersAddressService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MembersAddressResponseDto> createAddress(@RequestHeader("Authorization") String token, @RequestBody MembersAddressRequestDto requestDto) {
        MembersAddressResponseDto membersAddressResponseDto = membersAddressService.createAddress(token, requestDto);
        return new ResponseEntity<>(membersAddressResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/signup")
    public ResponseEntity<List<MembersAddressResponseDto>> getAddress(@RequestHeader("Authorization") String token) {
        List<MembersAddressResponseDto> membersAddressResponseDto = membersAddressService.getAddress(token);
        return new ResponseEntity<>(membersAddressResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MembersAddressDelResponseDto> delAddress(@RequestHeader("Authorization") String token, @RequestParam Long id) {
        MembersAddressDelResponseDto membersAddressDelResponseDto = membersAddressService.delAddress(token,id);
        return new ResponseEntity<>(membersAddressDelResponseDto, HttpStatus.OK);
    }


}
