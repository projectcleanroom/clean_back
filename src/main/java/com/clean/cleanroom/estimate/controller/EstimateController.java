package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.service.EstimateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    public  EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    //견적 승인
    @PostMapping
    public String approveEstimate (@RequestParam Long id) {
        return estimateService.approveEstimate(id);
        //서비스 로직으로 바로 반환하는 거 고민하기
        //String 말고 Dto로 반환하는게 MVC 패턴에 더 맞을 것 같음 -> 수정하기
    }

    //내 견적 전체 조회
    @GetMapping
    public List<EstimateResponseDto> getAllEstimates(@RequestParam Long id) {
        return estimateService.getAllEstimates(id);
    }
}
