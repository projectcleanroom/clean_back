package com.clean.cleanroom.members.service;

import com.clean.cleanroom.members.dto.MembersProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersSignupResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MembersService {
    private final MembersRepository membersRepository;

    public MembersService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Transactional
    public MembersSignupResponseDto signup(MembersRequestDto requestDto) {
        Members members = new Members(requestDto);
        members.setPassword(requestDto.getPassword());
        members.updateProfile(
                requestDto.getEmail(),
                requestDto.getNick(),
                requestDto.getPhoneNumber()
        );
        membersRepository.save(members);
        return new MembersSignupResponseDto(members);
    }

    @Transactional
    public MembersProfileResponseDto profile(Long id, MembersRequestDto requestDto) {
        Members members = membersRepository.findById(id).orElseThrow(
                () -> new RuntimeException("잘못된 ID : " + id)
        );
//         입력한 비밀번호가 현재 비밀번호와 일치하는지 확인
        if(!members.checkPassword(requestDto.getPassword())){
            throw new RuntimeException("잘못된 비밀번호");
        }
        members.members(
                requestDto.getEmail(),
                requestDto.getNick(),
                requestDto.getPhoneNumber()
        );

        membersRepository.save(members);

        return new MembersProfileResponseDto(
                members.getEmail(),
                members.getNick(),
                members.getPhoneNumber()
        );
    }
}
