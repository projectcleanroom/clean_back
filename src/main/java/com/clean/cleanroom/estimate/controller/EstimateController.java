package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.service.EstimateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }


    //견적 승인
    @PostMapping
    public EstimateResponseDto approveEstimate (@RequestParam Long id) {
        return estimateService.approveEstimate(id);
    }


    //내 견적 전체 조회
    @GetMapping
    public List<EstimateListResponseDto> getAllEstimates(@RequestParam Long id) {
        return estimateService.getAllEstimates(id);
    }
}
