package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.*;
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
        return new ResponseEntity<>(responseDtoList, HttpStatus.CREATED);
    }


    //청소의뢰 수정
    @PutMapping
    public ResponseEntity<List<CommissionUpdateResponseDto>> updateCommission(@RequestBody CommissionUpdateRequestDto requestDto) {
        List<CommissionUpdateResponseDto> responseDtoList = commissionService.updateCommission(requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //청소의뢰 취소
    @DeleteMapping("/{memberId}/{commissionId}")
    public ResponseEntity<List<CommissionCancelResponseDto>> cancelCommission(@RequestParam Long memberId, @RequestParam Long commissionId) {
        List<CommissionCancelResponseDto> responseDtoList = commissionService.cancelCommission(memberId, commissionId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //내 청소의뢰내역 조회
    @GetMapping("{memberId}")
    public ResponseEntity<List<MyCommissionResponseDto>> getMyCommission(@RequestParam Long memberId) {
        List<MyCommissionResponseDto> responseDtoList = commissionService.getMemberCommissions(memberId, MyCommissionResponseDto.class);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

}

