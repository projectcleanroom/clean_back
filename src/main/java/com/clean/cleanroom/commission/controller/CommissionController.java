package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.service.CommissionService;
import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import com.clean.cleanroom.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/commission")
public class CommissionController {

    private final CommissionService commissionService;
    private final TokenService tokenService;


    public CommissionController(CommissionService commissionService, TokenService tokenService) {
        this.commissionService = commissionService;
        this.tokenService = tokenService;
    }

    //청소의뢰 생성
    @PostMapping
    public ResponseEntity<List<CommissionCreateResponseDto>> createCommission(HttpServletRequest request, @RequestBody CommissionCreateRequestDto requestDto) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<CommissionCreateResponseDto> responseDtoList = commissionService.createCommission(email, requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.CREATED);
    }


    //청소의뢰 수정
    @PatchMapping
    public ResponseEntity<List<CommissionUpdateResponseDto>> updateCommission(
            HttpServletRequest request,
            @RequestParam Long commissionId,
            @RequestParam Long addressId,
            @RequestBody CommissionUpdateRequestDto requestDto) {

        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출
        List<CommissionUpdateResponseDto> responseDtoList = commissionService.updateCommission(email, commissionId, addressId, requestDto);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //청소의뢰 취소
    @DeleteMapping
    public ResponseEntity<List<CommissionCancelResponseDto>> cancelCommission(HttpServletRequest request, @RequestParam Long commissionId) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<CommissionCancelResponseDto> responseDtoList = commissionService.cancelCommission(email, commissionId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    // 내 청소의뢰내역 조회
    @GetMapping
    public ResponseEntity<List<MyCommissionResponseDto>> getMyCommission(HttpServletRequest request) {
        String email = tokenService.getEmailFromRequest(request); //헤더의 토큰에서 이메일 추출

        List<MyCommissionResponseDto> responseDtoList = commissionService.getMemberCommissionsByEmail(email, MyCommissionResponseDto.class);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    // 견적을 받은 청소 의뢰 리스트 조회
    @GetMapping("/confirmed")
    public ResponseEntity<List<CommissionConfirmListResponseDto>> getConfirmedCommissions(HttpServletRequest request) {
        String email = tokenService.getEmailFromRequest(request); // 헤더의 토큰에서 이메일 추출

        List<CommissionConfirmListResponseDto> responseDtoList = commissionService.getCommissionConfirmList(email);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    @GetMapping("/confirmdetail")
    public ResponseEntity<CommissionConfirmDetailResponseDto> getConfirmDetailCommissions(@RequestParam Long estimateId, Long commissionId) {
        CommissionConfirmDetailResponseDto responseDto = commissionService.getCommissionDetailConfirm(estimateId, commissionId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //img 업로드
    @PostMapping("/upload")
    public ResponseEntity<CommissionFileResponseDto> imgUpload(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) {
        CommissionFileResponseDto commissionFileResponseDto = commissionService.imgUpload(token, file);
        return new ResponseEntity<>(commissionFileResponseDto, HttpStatus.OK);
    }

    @GetMapping("/upload")
    public ResponseEntity<byte[]> imgGet(@RequestHeader ("Authorization") String token, @RequestParam String file) {
        CommissionFileGetResponseDto commissionFileGetResponseDto = commissionService.imgGet(token, file);
        // 파일의 MIME 타입 설정
        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get("/uploads/" + file));
            if (contentType == null) {
                contentType = "application/octet-stream";  // MIME 타입을 추정할 수 없을 때 기본값
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";  // 오류 발생 시 기본값
        }
        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, contentType);
        return new ResponseEntity<>(commissionFileGetResponseDto.getFileData(), headers, HttpStatus.OK);
    }

}