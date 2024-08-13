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

    @Comment("주소-상세")
    @Column(nullable = false, length = 500)
    private String addressDetail;

    @Comment("우편번호")
    @Column(nullable = false, length = 500)
    private String addressCode;

    public Address(String email, MembersAddressRequestDto requestDto) {
        this.email = email;
        this.address = requestDto.getAddress();
        this.addressDetail = requestDto.getAddressDetail();
        this.addressCode = requestDto.getAddressCode();
    }


    public void address(String email, MembersAddressRequestDto requestDto) {
        this.email = email;
        this.address = requestDto.getAddress();
        this.addressDetail = requestDto.getAddressDetail();
        this.addressCode = requestDto.getAddressCode();
    }

}
