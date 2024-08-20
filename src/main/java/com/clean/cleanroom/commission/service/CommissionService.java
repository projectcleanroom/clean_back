package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommissionService {

    private final CommissionRepository commissionRepository;
    private final MembersRepository membersRepository;
    private final AddressRepository addressRepository;
    private final JwtUtil jwtUtil;

    public CommissionService(CommissionRepository commissionRepository, MembersRepository membersRepository, AddressRepository addressRepository, JwtUtil jwtUtil) {
        this.commissionRepository = commissionRepository;
        this.membersRepository = membersRepository;
        this.addressRepository = addressRepository;
        this.jwtUtil = jwtUtil;
    }

    //청소의뢰 생성 서비스
    public List<CommissionCreateResponseDto> createCommission(String email, CommissionCreateRequestDto requestDto) {

        //의뢰한 회원찾기
        Members members = getMemberByEmail(email);

        //주소찾기
        Address address = getAddressById(requestDto.getAddressId());

        //청소의뢰 객채 생성
        Commission commission = new Commission(members, address, requestDto);

        //저장
        commissionRepository.save(commission);

        //내 청소의뢰내역 전체조회
        return getMemberCommissionsByEmail(email, CommissionCreateResponseDto.class);
    }

    //청소의로 수정 서비스
    @Transactional
    public List<CommissionUpdateResponseDto> updateCommission(String email, Long commissionId, Long addressId, CommissionUpdateRequestDto requestDto) {

        //수정할 회원 찾기
        Members members = getMemberByEmail(email);

        //회원이 신청한 청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        //수정할 주소가 존재하는지 확인
        Address address = getAddressById(addressId);

        //청소의뢰를 업데이트(요청데이터와, 수정주소)
        commission.update(requestDto, address);

        //내 청소의뢰내역 전체조회
        return getMemberCommissionsByEmail(email, CommissionUpdateResponseDto.class);
    }

    //청소의뢰 취소 서비스
    public List<CommissionCancelResponseDto> cancelCommission(String email, Long commissionId) {

        //회원 찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        //청소 의뢰 삭제
        commissionRepository.delete(commission);

        //내 청소의뢰내역 전체조회
        return getMemberCommissionsByEmail(email, CommissionCancelResponseDto.class);
    }

    //견적이 있는 청소의뢰 리스트 조회
    public List<CommissionConfirmListResponseDto> getCommissionConfirmList(String email) {
        Members members = getMemberByEmail(email);

        List<Commission> commissions = commissionRepository.findByMembers(members);

        // 견적이 있는 청소 의뢰 필터링 및 모든 견적 추가
        List<CommissionConfirmListResponseDto> responseList = new ArrayList<>();
        for (Commission commission : commissions) {
            for (Estimate estimate : commission.getEstimates()) {
                CommissionConfirmListResponseDto dto = convertToConfirmListDto(commission, estimate); // dto 변환
                responseList.add(dto); // 변환된 dto를 리스트에 추가
            }
        }
        return responseList;

    }


    // 특정 회원(나) 청소의뢰 내역 전체조회
    public <T> List<T> getMemberCommissionsByEmail(String email, Class<T> responseType) {

        //회원 찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기 (리스트로)
        List<Commission> commissions = commissionRepository.findByMembersId(members.getId())
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));

        return convertToDtoList(commissions, responseType);
    }


    //전체 청소의뢰를 조회하는 서비스
    public List<CommissionCreateResponseDto> getAllCommissions() {

        //청소의뢰 객체 전체 찾기
        List<Commission> commissions = commissionRepository.findAll();

        //찾은 청소 의뢰 객체들을 담아줄 DTO리스트 생성
        List<CommissionCreateResponseDto> responseDtoList = new ArrayList<>();
        for (Commission commission : commissions) {
            responseDtoList.add(new CommissionCreateResponseDto(commission)); //for 문으로 하나씩 담아주기
        }

        return responseDtoList;

    }

    //이메일로 회원은 찾는 메서드
    private Members getMemberByEmail(String email) {
        return membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));
    }

    //주소 찾는 메서드
    private Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ADDRESS_NOT_FOUND));
    }

    //회원이 신청한 청소의뢰를 찾는 메서드
    private Commission getCommissionByIdAndMember(Long commissionId, Members members) {
        return commissionRepository.findByIdAndMembersId(commissionId, members.getId())
                .orElseThrow(() -> new CustomException(ErrorMsg.COMMISSION_NOT_FOUND_OR_UNAUTHORIZED));
    }


    // 청소의뢰 내역 리스트를 -> 각각의 반환타입 DTO타입 리스트에 맞도록 변환해 담아주는 매서드
    private <T> List<T> convertToDtoList(List<Commission> commissions, Class<T> responseType) {
        List<T> responseDtoList = new ArrayList<>();
        for (Commission commission : commissions) {
            if (responseType == CommissionCreateResponseDto.class) {
                responseDtoList.add(responseType.cast(new CommissionCreateResponseDto(commission)));
            } else if (responseType == CommissionUpdateResponseDto.class) {
                responseDtoList.add(responseType.cast(new CommissionUpdateResponseDto(commission)));
            } else if (responseType == CommissionCancelResponseDto.class) {
                responseDtoList.add(responseType.cast(new CommissionCancelResponseDto(commission)));
            } else if (responseType == MyCommissionResponseDto.class) {
                responseDtoList.add(responseType.cast(new MyCommissionResponseDto(commission)));
            }
        }
        return responseDtoList;
    }


    // Commission과 Estimate를 받아서 CommissionConfirmListResponseDto로 변환하는 메서드
    private CommissionConfirmListResponseDto convertToConfirmListDto(Commission commission, Estimate estimate) {
        List<EstimateResponseDto> estimateResponseDtos = new ArrayList<>();
        estimateResponseDtos.add(new EstimateResponseDto(estimate));

        return new CommissionConfirmListResponseDto(
                commission.getId(),
                commission.getSize(),
                commission.getHouseType(),
                commission.getCleanType(),
                commission.getDesiredDate(),
                commission.getSignificant(),
                commission.getImage(),
                commission.getStatus(),
                estimateResponseDtos
        );
    }

    public CommissionConfirmDetailResponseDto getCommissionDetailConfirm(Long estimateId, Long commissionId) {

        Commission commission = commissionRepository.findByEstimatesIdAndId(estimateId, commissionId);

        Estimate estimate = new Estimate();
        Address address = new Address();
        CommissionConfirmDetailResponseDto commissionConfirmDetailResponseDto = new CommissionConfirmDetailResponseDto(commission, estimate, address);
        return commissionConfirmDetailResponseDto;
    }




    private static final Logger logger = LoggerFactory.getLogger(CommissionService.class);
    private static final String UPLOAD_DIR = "uploads/";
    public CommissionFileResponseDto imgUpload(String token, MultipartFile file) {
        String email = jwtUtil.extractEmail(token);
        try {
            // 파일 저장 로직
            saveFile(file);
            String filePath = UPLOAD_DIR + file.getOriginalFilename(); // 파일 경로
            logger.info("File successfully uploaded to: " + filePath);
            return new CommissionFileResponseDto(file.getOriginalFilename(), "File uploaded successfully");
        } catch (IOException e) {
            logger.error("File upload failed due to IOException: ", e);
            return new CommissionFileResponseDto(null, "File upload failed");
        }
    }

    private void saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdir();  // 디렉토리가 없으면 생성
        }
        File destinationFile = new File(UPLOAD_DIR + file.getOriginalFilename());
        file.transferTo(destinationFile);  // 파일 저장
        logger.info("File transferred to: " + destinationFile.getAbsolutePath());
    }


}
