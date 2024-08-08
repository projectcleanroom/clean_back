package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersGetProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersSignupResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MembersService {
    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    public MembersService(MembersRepository membersRepository, JwtUtil jwtUtil) {
        this.membersRepository = membersRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public MembersSignupResponseDto signup(MembersRequestDto requestDto) {
        // email 유무
        if (membersRepository.existsByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorMsg.DUPLICATE_EMAIL);
        }
        // Nick 존재 유무
        if (membersRepository.existsByNick(requestDto.getNick())){
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }
        // phoneNumber 존재 유무
        if (membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())){
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // 패스워드 Hashing
        Members members = new Members(requestDto);
        members.setPassword(requestDto.getPassword());
        membersRepository.save(members);
        return new MembersSignupResponseDto(members);
    }

    @Transactional
    public MembersProfileResponseDto profile(String token, MembersRequestDto requestDto) {
        String email = jwtUtil.extractEmail(token);
        // email 유무
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID));
        // Nick 존재 유무
        if (!members.getNick().equals(requestDto.getNick()) &&
                membersRepository.existsByNick(requestDto.getNick())){
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }
        // phoneNumber 존재 유무
        if (!members.getPhoneNumber().equals(requestDto.getPhoneNumber()) &&
                membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())){
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // 비밀번호 일치 확인
        if (!members.checkPassword(requestDto.getPassword())) {
            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
        }
        members.members(requestDto);
        membersRepository.save(members);
        return new MembersProfileResponseDto(members);
    }


    public MembersGetProfileResponseDto getProfile(String token) {
        // email 존재 유무
        String email = jwtUtil.extractEmail(token);
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID)
        );
        return new MembersGetProfileResponseDto(members);
    }
}
