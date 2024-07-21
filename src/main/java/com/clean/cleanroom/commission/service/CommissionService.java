package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionCreateResponseDto;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        return getAllCommissions();
    }

    //전체 청소의뢰를 조회하는 서비스
    private List<CommissionCreateResponseDto> getAllCommissions() {

        //청소의뢰 객체 전체 찾기
        List<Commission> commissions = commissionRepository.findAll();

        //찾은 청소 의뢰 객체들을 담아줄 리스트 생성
        List<CommissionCreateResponseDto> responseDtoList = new ArrayList<>();

        for (Commission commission : commissions) {
            responseDtoList.add(convertToResponseDto(commission)); //for 문으로 하나씩 담아주기
        }

        return responseDtoList;

    }

    //청소객체를 -> DTO로 반환하는 메서드
    private CommissionCreateResponseDto convertToResponseDto(Commission commission) {

        CommissionCreateResponseDto responseDto = new CommissionCreateResponseDto(commission);

        return responseDto;
    }

}
