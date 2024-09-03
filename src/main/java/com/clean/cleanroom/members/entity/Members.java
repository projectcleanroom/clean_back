package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.members.dto.MembersSignupRequestDto;
import com.clean.cleanroom.members.dto.MembersUpdateProfileRequestDto;
import com.clean.cleanroom.util.PasswordUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 15, unique = true)
    private String nick;

    @Column(length = 15, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private LoginType loginType;

    @Column(length = 255, nullable = true) // password 필드를 nullable로 설정
    private String password;

    @Column(nullable = true, unique = true)
    private String kakaoId;

    @ManyToOne
    private Account selectedAccount;

    // 일반 유저용 생성자
    public Members(MembersSignupRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.nick = requestDto.getNick();
        this.loginType = requestDto.getLoginType();
        this.phoneNumber = requestDto.getPhoneNumber();

        if (this.loginType == LoginType.REGULAR) {
            this.password = PasswordUtil.encodePassword(requestDto.getPassword());
        } else {
            this.password = null; // 카카오 유저는 비밀번호가 null
        }
        this.kakaoId = null;
    }

    // 카카오 유저용 생성자
    public Members(String email, String nick, String phoneNumber, String kakaoId, LoginType loginType) {
        this.email = email;
        this.nick = nick;
        this.phoneNumber = phoneNumber;
        this.kakaoId = kakaoId;
        this.loginType = loginType;
        this.password = null; // 카카오 유저는 비밀번호가 없음
    }

    public Members(MembersSignupRequestDto requestDto, String encodedPassword) {
        this.email = requestDto.getEmail();
        this.nick = requestDto.getNick();
        this.loginType = requestDto.getLoginType();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.password = encodedPassword;
        this.kakaoId = null; // 카카오 ID는 null로 설정
    }

    // 회원 정보를 업데이트하는 메서드
    public void updateMembers(MembersUpdateProfileRequestDto requestDto) {
        if (requestDto.getNick() != null) {
            this.nick = requestDto.getNick();
        }
        if (requestDto.getPhoneNumber() != null) {
            this.phoneNumber = requestDto.getPhoneNumber();
        }
    }

    // 비밀번호 설정 메서드
    public void setPassword(String password) {
        this.password = PasswordUtil.encodePassword(password);
    }

    // 계좌 설정 메서드
    public void SelectedAccount(Account account) {
        this.selectedAccount = account;
    }

    // 카카오 ID 설정 메서드 (카카오 유저 전용)
    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }
}

