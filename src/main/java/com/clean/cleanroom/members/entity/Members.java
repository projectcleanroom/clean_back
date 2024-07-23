package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.util.PasswordUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email
    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
//    적용하지 정상작동하지 않아, 추 후 수정할 예정
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@$%^&*_])[a-zA-Z\\d~!@$%^&*_]{8,}$",
//            message = "비밀번호는 최소 8자 이상이어야 하며, 소문자, 숫자, 특수 문자(~!@$%^&*_)를 포함해야 합니다.")
    @Column(nullable = false, length = 255)
    private String password;

    @NotEmpty(message = "닉네임은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_-]{1,15}$",
            message = "닉네임은 숫자, 대문자, 소문자, 한글, 밑줄(_), 대시(-)만 포함할 수 있으며, 길이는 1자에서 15자 사이여야 합니다.")
    @Column(nullable = false, length = 15, unique = true)
    private String nick;

    @NotEmpty(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^01\\d{1}-\\d{4}-\\d{4}$",
            message = "전화번호는 010-1234-5678 형식이어야 합니다.")
    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "members")
    private List<Address> address;

    public Members(MembersRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.nick = requestDto.getNick();
        this.phoneNumber = requestDto.getPhoneNumber();
    }

    public boolean checkPassword(String password) {
        return PasswordUtil.matches(password, this.password);
    }

    public void members(String email, String nick, String phoneNumber) {
        this.email = email;
        this.nick = nick;
        this.phoneNumber = phoneNumber;
    }

    public void updateProfile(String email, String nick, String phoneNumber) {
        this.email = email;
        this.nick = nick;
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.encodePassword(password);
    }
}
