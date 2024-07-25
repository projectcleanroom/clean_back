package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersSignupResponseDto;
import com.clean.cleanroom.members.dto.MembersProfileResponseDto;
import com.clean.cleanroom.members.service.MembersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    private final MembersService membersService;
    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @PostMapping("/signup")
    public MembersSignupResponseDto signup(@RequestBody MembersRequestDto requestDto) {
        return membersService.signup(requestDto);
    }

    @PutMapping("/profile")
    public MembersProfileResponseDto profile(@RequestParam Long id, @RequestBody MembersRequestDto requestDto) {
        return membersService.profile(id, requestDto);
    }
}
