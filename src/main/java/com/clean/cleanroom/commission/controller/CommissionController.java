package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionCreateResponseDto;
import com.clean.cleanroom.commission.dto.CommissionUpdateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionUpdateResponseDto;
import com.clean.cleanroom.commission.service.CommissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission")
public class CommissionController {

    private final CommissionService commissionService;

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    //청소의뢰 생성
    @PostMapping
    public ResponseEntity<List<CommissionCreateResponseDto>> createCommission(@RequestBody CommissionCreateRequestDto requestDto) {
            List<CommissionCreateResponseDto> responseDtoList = commissionService.createCommission(requestDto);
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    //청소의뢰 수정
    @PutMapping
    public ResponseEntity<List<CommissionUpdateResponseDto>> updateCommission(@RequestBody CommissionUpdateRequestDto requestDto) {
        List<CommissionUpdateResponseDto> responseDtoList = commissionService.updateCommission(requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
}

