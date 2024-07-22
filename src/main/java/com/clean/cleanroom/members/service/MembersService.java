package com.clean.cleanroom.members.service;

import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersSignupResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.springframework.stereotype.Service;


@Service
public class MembersService {
    private final MembersRepository membersRepository;

    public MembersService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    public MembersSignupResponseDto signup(MembersRequestDto requestDto) {
        Members members = new Members(requestDto);
        membersRepository.save(members);
        return new MembersSignupResponseDto(members);
    }
}
