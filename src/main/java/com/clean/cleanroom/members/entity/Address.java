package com.clean.cleanroom.members.entity;


import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("이메일")
    @Column(nullable = false, length = 25)
    private String email;

    @Comment("주소")
    @Column(nullable = false, length = 500)
    private String address;

    public Address(String email, MembersAddressRequestDto requestDto) {
        this.email = email;
        this.address = requestDto.getAddress();
    }


    public void address(MembersAddressRequestDto requestDto) {
        this.address = requestDto.getAddress();
    }

}
