package com.clean.cleanroom.members.service;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembersService {

    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    public MembersLoginResponseDto login(MembersLoginRequestDto requestDto) {
        Members member = membersRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!requestDto.getPassword().equals(member.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(member.getEmail());
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }

}

