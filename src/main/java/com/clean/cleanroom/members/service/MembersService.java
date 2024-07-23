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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        // 평문 비교. merge 후 수정할 부분
        if (!requestDto.getPassword().equals(member.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }

        return new MembersLoginResponseDto(
                member.getEmail(),
                member.getNick()
        );
    }
}

