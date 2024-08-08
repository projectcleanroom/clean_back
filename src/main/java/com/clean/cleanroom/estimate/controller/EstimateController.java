package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.service.EstimateService;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/estimate")
public class EstimateController {

    private final EstimateService estimateService;
    private final JwtUtil jwtUtil;

    public EstimateController(EstimateService estimateService, JwtUtil jwtUtil) {
        this.estimateService = estimateService;
        this.jwtUtil = jwtUtil;
    }


    //견적 승인
    @PostMapping
    public ResponseEntity<EstimateResponseDto> approveEstimate (@RequestHeader("Authorization") String token, @RequestParam Long id) {
        EstimateResponseDto estimateResponseDto = estimateService.approveEstimate(token, id);
        return new ResponseEntity<>(estimateResponseDto, HttpStatus.OK);
    }


    //내 견적 전체 조회
    @GetMapping
    public ResponseEntity<List<EstimateListResponseDto>> getAllEstimates(@RequestHeader("Authorization") String token, Long id) {
        List<EstimateListResponseDto> estimateListResponseDtos = estimateService.getAllEstimates(token, id);
        return new ResponseEntity<>(estimateListResponseDtos, HttpStatus.OK);
    }
}
