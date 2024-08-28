package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateDetailResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final CommissionRepository commissionRepository;
    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    public EstimateService(EstimateRepository estimateRepository,
                           CommissionRepository commissionRepository,
                           MembersRepository membersRepository,
                           JwtUtil jwtUtil) {
        this.estimateRepository = estimateRepository;
        this.commissionRepository = commissionRepository;
        this.membersRepository = membersRepository;
        this.jwtUtil = jwtUtil;
    }


    //견적 승인
    public EstimateResponseDto approveEstimate(String token, Long id) {

        String email = jwtUtil.extractEmail(token);

        //email로 회원 찾기
        Members members = getMemberByEmail(email);

        //견적 내역 찾기
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        Commission commission = estimate.getCommission();// 견적에 연결된 Commission 가져오기

        Members owner = commission.getMembers(); // Commission에 연결된 회원 가져오기

        if (!owner.getId().equals(members.getId())) { // 견적의 소유자가 요청한 회원인지 확인
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        // 승인 상태로 변경 (엔티티의 메서드를 통해 변경)
        estimate.approve();

        estimateRepository.save(estimate); //estimate 레포지토리에 저장

        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate); //estimate 객체를 Dto로 변환

        return estimateResponseDto;
    }


    //견적 내역 조회
    public List<EstimateListResponseDto> getAllEstimates(String token, Long commissionId) {

        String email = jwtUtil.extractEmail(token);

        //email로 회원 찾기
        Members members = getMemberByEmail(email);

        //의뢰 내역 찾기
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND));

        //의뢰 객체에서 member 필드 가져오기
        Members owner = commission.getMembers();

        if (!owner.getId().equals(members.getId())) { // 견적의 소유자가 요청한 회원인지 확인
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        //commission에 해당하는 견적들을 리스트에 담기
        List<Estimate> estimates = estimateRepository.findByCommissionId(commission);

        //견적이 비어있는지 확인
        if (estimates.isEmpty()) {
            throw new CustomException(ErrorMsg.NO_ESTIMATES_FOUND);
        }

        //DTO로 반환하기 위해 변환
        List<EstimateListResponseDto> estimateListResponseDtos = new ArrayList<>();

        for (Estimate estimate : estimates) {
            EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);
            estimateListResponseDtos.add(estimateListResponseDto);
        }
        return estimateListResponseDtos;
    }


    // 견적 단건 조회
    public EstimateDetailResponseDto getEstimateById (String token, Long id) {

        String email = jwtUtil.extractEmail(token);

        Members members = getMemberByEmail(email);

        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ESTIMATE_NOT_FOUND));

        // 견적에 연결된 의뢰 가져오기
        Commission commission = estimate.getCommission();

        Partner partner = estimate.getPartner();

        // 의뢰의 소유자 가져오기
        Members owner = commission.getMembers();

        // 의뢰의 소유자와 이용자가 같은 사람인지 확인
        if (!owner.getId().equals(members.getId())) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        return new EstimateDetailResponseDto (estimate, commission, partner);
    }



    //email로 회원 찾기
    private Members getMemberByEmail(String email) {
        return membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));
    }
}


