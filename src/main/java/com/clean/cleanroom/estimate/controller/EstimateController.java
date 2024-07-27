package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.service.EstimateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    //파트너 로직 추가시 수정 (서비스까지)


    //견적 승인
    @PostMapping
    public ResponseEntity<EstimateResponseDto> approveEstimate (HttpServletRequest httpServletRequest) {
        EstimateResponseDto estimateResponseDto = estimateService.approveEstimate(httpServletRequest);
        return new ResponseEntity<>(estimateResponseDto, HttpStatus.OK);
    }


    //내 견적 전체 조회
    @GetMapping
    public ResponseEntity<List<EstimateListResponseDto>> getAllEstimates(HttpServletRequest httpServletRequest, Long commissionId) {
        List<EstimateListResponseDto> estimateListResponseDtos = estimateService.getAllEstimates(httpServletRequest, commissionId);
        return new ResponseEntity<>(estimateListResponseDtos, HttpStatus.OK);
    }
}
