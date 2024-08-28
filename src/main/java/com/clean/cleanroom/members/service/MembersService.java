package com.clean.cleanroom.members.service;

import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.*;
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
    public MembersSignupResponseDto signup(MembersSignupRequestDto requestDto) {
        // 일반 유저만 처리
        if (requestDto.getLoginType() != LoginType.REGULAR) {
            throw new CustomException(ErrorMsg.INVALID_SIGNUP_REQUEST);
        }
        // email 유무
        if (membersRepository.existsByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorMsg.DUPLICATE_EMAIL);
        }
        // Nick 존재 유무
        if (membersRepository.existsByNick(requestDto.getNick())){
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }
        // phoneNumber 존재 유무
        if (requestDto.getPhoneNumber() != null && membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // 일반 회원으로 등록 (비밀번호는 필수이므로 바로 설정)
        Members member = new Members(requestDto);
        member.setPassword(requestDto.getPassword());

        membersRepository.save(member);
        return new MembersSignupResponseDto(member);
    }

    @Transactional
    public MembersProfileResponseDto profile(String token, MembersUpdateProfileRequestDto requestDto) {
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
        if (requestDto.getPhoneNumber() != null &&
                !requestDto.getPhoneNumber().equals(members.getPhoneNumber()) &&
                membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // 비밀번호 일치 확인
//        if (!members.checkPassword(requestDto.getPassword())) {
//            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
//        }

        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            members.setPassword(requestDto.getPassword());
        }
        members.updateMembers(requestDto);
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
