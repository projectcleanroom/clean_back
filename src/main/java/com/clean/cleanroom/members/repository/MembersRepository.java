package com.clean.cleanroom.members.repository;

import com.clean.cleanroom.members.dto.MemberIdAndNickDto;
import com.clean.cleanroom.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNick(String nick);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT new com.clean.cleanroom.members.dto.MemberIdAndNickDto(m.id, m.nick) from Members m WHERE m.email = :email")
    MemberIdAndNickDto findMemberIdByEmailNative(@Param("email") String email);
}
