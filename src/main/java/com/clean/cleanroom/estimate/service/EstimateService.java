package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {


    private EstimateRepository estimateRepository;

    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }


    //견적 승인
    public String approveEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("견적 내역을 찾을 수 없습니다."));

        estimateRepository.save(estimate);

        return "견적이 승인 되었습니다.";
    }


    //견적 내역 조회
    public List<EstimateResponseDto> getAllEstimates() {

        List<Estimate> estimates = estimateRepository.findAll();

        List<EstimateResponseDto> estimateResponseDtos = new ArrayList<>();

        for (Estimate estimate : estimates) {
            EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);
            estimateResponseDtos.add(estimateResponseDto);
        }

        return estimateResponseDtos;
    }
}


