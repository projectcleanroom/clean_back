package com.clean.cleanroom.members.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
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


    //테스트 전용 생성자 (주소 없음)
    public Members(Long id, String email, String password, String nick, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.phoneNumber = phoneNumber;
    }
}
