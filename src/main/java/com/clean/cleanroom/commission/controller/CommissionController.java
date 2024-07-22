package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.service.CommissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission")
@Tag(name = "Commission", description = "청소 의뢰 API")
public class CommissionController {

    private final CommissionService commissionService;

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    //청소의뢰 생성
    @PostMapping
    @Operation(summary = "청소 의뢰 생성", description = "새로운 청소 의뢰를 생성합니다.")
    public ResponseEntity<List<CommissionCreateResponseDto>> createCommission(@RequestBody CommissionCreateRequestDto requestDto) {
        List<CommissionCreateResponseDto> responseDtoList = commissionService.createCommission(requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    //청소의뢰 수정
    @PutMapping
    @Operation(summary = "청소 의뢰 수정", description = "기존 청소 의뢰를 수정합니다.")
    public ResponseEntity<List<CommissionUpdateResponseDto>> updateCommission(@RequestBody CommissionUpdateRequestDto requestDto) {
        List<CommissionUpdateResponseDto> responseDtoList = commissionService.updateCommission(requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //청소의뢰 취소
    @DeleteMapping("/{memberId}/{commissionId}")
    @Operation(summary = "청소 의뢰 취소", description = "특정 청소 의뢰를 취소합니다.")
    public ResponseEntity<List<CommissionCancelResponseDto>> cancelCommission(@PathVariable Long memberId, @PathVariable Long commissionId) {
        List<CommissionCancelResponseDto> responseDtoList = commissionService.cancelCommission(memberId, commissionId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //내 청소의뢰내역 조회
    @GetMapping("{memberId}")
    @Operation(summary = "내 청소 의뢰 내역 조회", description = "특정 회원의 청소 의뢰 내역을 조회합니다.")
    public ResponseEntity<List<MyCommissionResponseDto>> getMyCommission(@PathVariable Long memberId) {
        List<MyCommissionResponseDto> responseDtoList = commissionService.getMemberCommissions(memberId, MyCommissionResponseDto.class);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

}

