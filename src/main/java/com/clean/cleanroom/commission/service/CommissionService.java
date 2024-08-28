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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
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
    public CommissionCreateResponseDto createCommission(String email, CommissionCreateRequestDto requestDto) {

        //의뢰한 회원찾기
        Members members = getMemberByEmail(email);

        //주소찾기
        Address address = getAddressById(requestDto.getAddressId());

        //청소의뢰 객채 생성 + 저장
        saveCommission(members, address, requestDto);

        return new CommissionCreateResponseDto();
    }

    //청소의로 수정 서비스
    @Transactional
    public CommissionUpdateResponseDto updateCommission(String email, Long commissionId, Long addressId, CommissionUpdateRequestDto requestDto) {

        //수정할 회원 찾기
        Members members = getMemberByEmail(email);

        //회원이 신청한 청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        //수정할 주소가 존재하는지 확인
        Address address = getAddressById(addressId);

        //청소의뢰를 업데이트(요청데이터와, 수정주소)
        commission.update(requestDto, address);

        //업데이트 된 청소의뢰 엔티티를 -> DTO로 변환해 반환하기
        CommissionUpdateResponseDto responseDto = new CommissionUpdateResponseDto(commission);

        return responseDto;
    }

    //청소의뢰 취소 서비스
    public CommissionCancelResponseDto cancelCommission(String email, Long commissionId) {

        //회원 찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        //청소 의뢰 삭제
        commissionRepository.delete(commission);

        //메시지 반환
        return new CommissionCancelResponseDto();
    }

    // 특정 회원(나) 청소의뢰 내역 전체조회
    @Transactional(readOnly = true)
    public  List<MyCommissionResponseDto> getMemberCommissionsByEmail(String email) {

        //회원 찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기 (리스트로)
        List<Commission> commissions = commissionRepository.findByMembersId(members.getId())
                .orElseThrow(() -> new CustomException(ErrorMsg.MEMBER_NOT_FOUND));

        // Commission 리스트를 MyCommissionResponseDto 리스트로 변환
        List<MyCommissionResponseDto> commissionResponseDtos = new ArrayList<>();
        for (Commission commission : commissions) {
            commissionResponseDtos.add(new MyCommissionResponseDto(commission));
        }

        //변환된 Dto리스트 반환
        return commissionResponseDtos;
    }


    //전체 청소의뢰를 조회하는 서비스
    public List<MyCommissionResponseDto> getAllCommissions() {

        //청소의뢰 객체 전체 찾기
        List<Commission> commissions = commissionRepository.findAll();

        //찾은 청소 의뢰 객체들을 담아줄 DTO리스트 생성
        List<MyCommissionResponseDto> responseDtoList = new ArrayList<>();
        for (Commission commission : commissions) {
            responseDtoList.add(new MyCommissionResponseDto(commission)); //for 문으로 하나씩 담아주기
        }

        return responseDtoList;

    }

    //청소의뢰 단건조회
    public CommissionConfirmDetailResponseDto getCommissionDetailConfirm(String email, Long commissionId) {

        //이메일로 회원찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        //DTO로 변환하기
        CommissionConfirmDetailResponseDto responseDto = new CommissionConfirmDetailResponseDto(commission);

        //반환하기
        return responseDto;

    }

    //청소견적이 리스트로 붙어있는 청소의뢰 단건조회
    public CommissionConfirmListResponseDto getCommissionConfirmList(String email, Long commissionId) {

        //회원 찾기
        Members members = getMemberByEmail(email);

        //청소의뢰 객체 찾기
        Commission commission = getCommissionByIdAndMember(commissionId, members);

        // 청소의뢰에 속한 견적 리스트 변환
        List<EstimateResponseDto> estimateDtos = new ArrayList<>();
        for (Estimate estimate : commission.getEstimates()) {
            EstimateResponseDto estimateDto = new EstimateResponseDto(estimate);
            estimateDtos.add(estimateDto);
        }

        //DTO로 변환
        CommissionConfirmListResponseDto responseDto = new CommissionConfirmListResponseDto(commission, estimateDtos);

        return responseDto;
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


    private static final Logger logger = LoggerFactory.getLogger(CommissionService.class);
    private static final String UPLOAD_DIR = "/uploads/";
    public CommissionFileResponseDto imgUpload(String token, MultipartFile file) {
        String email = jwtUtil.extractEmail(token);
        try {
            // 이미지 파일만 허용
            if (!isImageFile(file)) {
                return new CommissionFileResponseDto(null, "Only image files are allowed.");
            }

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
            directory.mkdirs();  // 디렉토리가 없으면 생성
        }
        File destinationFile = new File(UPLOAD_DIR + file.getOriginalFilename());
        file.transferTo(destinationFile);  // 파일 저장
        logger.info("File transferred to: " + destinationFile.getAbsolutePath());
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif"));
    }


    public CommissionFileGetResponseDto imgGet(String token, String file) {
        String email = jwtUtil.extractEmail(token);
        Path filePath = Paths.get(UPLOAD_DIR + file);
        try {
            // 파일이 존재하는지 확인
            if (!Files.exists(filePath)) {
                logger.error("File not found: " + file);
                return new CommissionFileGetResponseDto(file, "File not found", null);
            }

            // 파일을 읽어 바이트 배열로 변환
            byte[] fileData = Files.readAllBytes(filePath);
            logger.info("File successfully retrieved: " + file);

            // 성공 메시지와 함께 DTO 반환
            return new CommissionFileGetResponseDto(file, "File retrieved successfully", fileData);

        } catch (IOException e) {
            // 파일 읽기 실패 시 처리
            logger.error("Failed to read file: " + file, e);
            return new CommissionFileGetResponseDto(file, "Failed to retrieve file", null);
        }

    }

    //필요한 부분만 트랜잭셔널 처리를 하도록 save메서드를 따로 빼기
    @Transactional
    protected void saveCommission(Members members, Address address, CommissionCreateRequestDto requestDto) {
        Commission commission = new Commission(members, address, requestDto);
        commissionRepository.save(commission);
    }
}
