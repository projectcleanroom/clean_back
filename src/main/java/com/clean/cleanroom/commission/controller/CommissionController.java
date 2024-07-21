package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionCreateResponseDto;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.service.CommissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commission")
public class CommissionController {

    private final CommissionService commissionService;

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @PostMapping
    public ResponseEntity<List<CommissionCreateResponseDto>> createCommission(@RequestBody CommissionCreateRequestDto requestDto) {

            List<CommissionCreateResponseDto> responseDtoList = commissionService.createCommission(requestDto);
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);

    }
}
