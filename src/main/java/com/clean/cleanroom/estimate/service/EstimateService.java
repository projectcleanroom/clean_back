package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final CommissionRepository commissionRepository;

    public EstimateService(EstimateRepository estimateRepository, CommissionRepository commissionRepository) {
        this.estimateRepository = estimateRepository;
        this.commissionRepository = commissionRepository;
    }


    //견적 승인
    public EstimateResponseDto approveEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));
        estimateRepository.save(estimate);
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);
        return estimateResponseDto;
    }


    //견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimates(Long commissionId) {

        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        List<Estimate> estimates = estimateRepository.findByCommissionId(commission);

        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.NO_ESTIMATES_FOUND);
        }
        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();
        for (Estimate estimate : estimates) {
            EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);
            estimateListResponseDtos.add(estimateListResponseDto);
        }
        return estimateListResponseDtos;
    }
}


