package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommissionService {

    private final CommissionRepository commissionRepository;
    private final MembersRepository membersRepository;
    private final AddressRepository addressRepository;

    public CommissionService(CommissionRepository commissionRepository, MembersRepository membersRepository, AddressRepository addressRepository) {
        this.commissionRepository = commissionRepository;
        this.membersRepository = membersRepository;
        this.addressRepository = addressRepository;
    }

    //청소의뢰 생성 서비스
    public List<CommissionCreateResponseDto> createCommission(CommissionCreateRequestDto requestDto) {

        //의뢰한 회원찾기
        Members members = membersRepository.findById(requestDto.getMembersId())
                .orElseThrow(() -> new CustomException("회원을 찾을 수 없습니다."));

        //주소찾기
        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new CustomException("주소를 찾을 수 없습니다."));

        //청소의뢰 객채 생성
        Commission commission = new Commission(members, address, requestDto);

        //저장
        commissionRepository.save(commission);

        return getMemberCommissions(members.getId(), CommissionCreateResponseDto.class); //내 청소의뢰내역 전체조회
    }

    //청소의로 수정 서비스
    @Transactional
    public List<CommissionUpdateResponseDto> updateCommission(CommissionUpdateRequestDto requestDto) {

        //청소의뢰 내역찾기
        Commission commission = commissionRepository.findById(requestDto.getCommissionId())
                .orElseThrow(() -> new CustomException("청소의뢰가 존재하지 않습니다."));

        //수정한 주소 찾기
        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new CustomException("주소를 찾을 수 없습니다."));

        commission.update(requestDto, address); //청소의뢰를 업데이트(요청데이터와, 찾은주소)

        return getMemberCommissions(commission.getMembers().getId(), CommissionUpdateResponseDto.class); //내 청소의뢰내역 전체조회
    }

    //청소의뢰 취소 서비스
    public List<CommissionCancelResponseDto> cancelCommission(Long memberId, Long commissionId) {

        Commission commission = commissionRepository.findByIdAndMembersId(commissionId, memberId)
                .orElseThrow(() -> new CustomException("청소의뢰가 존재하지 않거나 권한이 없습니다."));

        commissionRepository.delete(commission);//청소 내역 삭제

        return getMemberCommissions(memberId, CommissionCancelResponseDto.class); //내 청소의뢰내역 전체조회
    }


    //특정 회원(나) 청소의뢰 내역 전체조회
    public <T> List<T> getMemberCommissions(Long memberId, Class<T> responseType) {

        //특정 회원의 청소의뢰 내역 조회하기
        List<Commission> commissions = commissionRepository.findByMembersId(memberId)
                .orElseThrow(()-> new CustomException("회원을 찾을 수 없습니다."));

        //청소 내역 DTO를 담을 리스트
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


    //전체 청소의뢰를 조회하는 서비스
    private List<CommissionCreateResponseDto> getAllCommissions() {

        //청소의뢰 객체 전체 찾기
        List<Commission> commissions = commissionRepository.findAll();

        //찾은 청소 의뢰 객체들을 담아줄 DTO리스트 생성
        List<CommissionCreateResponseDto> responseDtoList = new ArrayList<>();
        for (Commission commission : commissions) {
            responseDtoList.add(new CommissionCreateResponseDto(commission)); //for 문으로 하나씩 담아주기
        }

        return responseDtoList;

    }

}
