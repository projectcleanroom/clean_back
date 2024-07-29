//package com.clean.cleanroom.estimate.service;
//
//import com.clean.cleanroom.commission.entity.Commission;
//import com.clean.cleanroom.commission.repository.CommissionRepository;
//import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
//import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
//import com.clean.cleanroom.estimate.entity.Estimate;
//import com.clean.cleanroom.estimate.repository.EstimateRepository;
//import com.clean.cleanroom.exception.CustomException;
//import com.clean.cleanroom.exception.ErrorMsg;
//import com.clean.cleanroom.util.JwtUtil;
//import com.clean.cleanroom.commission.repository.CommissionRepository;
//import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
//import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
//import com.clean.cleanroom.estimate.entity.Estimate;
//import com.clean.cleanroom.estimate.repository.EstimateRepository;
//import com.clean.cleanroom.exception.CustomException;
//import com.clean.cleanroom.exception.ErrorMsg;
//import com.clean.cleanroom.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class EstimateService {
//
//    private final EstimateRepository estimateRepository;
////    private final CommissionRepository commissionRepository;
//    private final JwtUtil jwtUtil;
//
//    public EstimateService(EstimateRepository estimateRepository,
//                           CommissionRepository commissionRepository,
//                           JwtUtil jwtUtil) {
//        this.estimateRepository = estimateRepository;
////        this.commissionRepository = commissionRepository;
//        this.jwtUtil = jwtUtil;
//    }
//
//
//    //견적 승인
//    public EstimateResponseDto approveEstimate(HttpServletRequest httpServletRequest) {
//
//        String email = getEmailFromRequest(httpServletRequest);
//
//        Estimate estimate = estimateRepository.findByEmail(email)
//                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));
//
//        estimateRepository.save(estimate);
//
//        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);
//        return estimateResponseDto;
//    }
//
//
//    //견적 내역 조회
//    public List<EstimateListResponseDto> getAllEstimates(HttpServletRequest httpServletRequest, Long commissionId) {
//
//        String email = getEmailFromRequest(httpServletRequest);
//
//        List<Estimate> estimates = estimateRepository.findByEmailAndCommissionId(email, commissionId);
//
//        if (estimates.isEmpty()) {
//            throw new CustomException(ErrorMsg.COMMISSION_NOT_FOUND);
//        }
//
//        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();
//
//        for (Estimate estimate : estimates) {
//            EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);
//            estimateListResponseDtos.add(estimateListResponseDto);
//        }
//
//        return estimateListResponseDtos;
//    }
//
//    //요청헤더에서 토큰안의 이메일을 추출하는 메서드
//    private String getEmailFromRequest(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        String token = header.substring(7);
//        return jwtUtil.getEmailFromToken(token);
//    }
//}