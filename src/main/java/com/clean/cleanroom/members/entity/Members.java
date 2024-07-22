package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.members.dto.MembersRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String nick;

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

}
