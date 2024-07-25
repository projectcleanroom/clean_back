package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    private final EstimateRepository estimateRepository;

    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }


    //견적 승인
    public EstimateResponseDto approveEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("견적 내역을 찾을 수 없습니다."));

        estimateRepository.save(estimate);
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);

        return estimateResponseDto;
    }


    //견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimates(Long id) {

        List<Estimate> estimates = estimateRepository.findByCommissionId(id);

        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();

        for (Estimate estimate : estimates) {
            EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);
            estimateListResponseDtos.add(estimateListResponseDto);
        }

        return estimateListResponseDtos;
    }
}


