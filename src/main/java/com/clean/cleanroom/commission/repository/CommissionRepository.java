package com.clean.cleanroom.commission.repository;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    Optional<List<Commission>> findByMembersId(Long membersId);

    Optional<Commission> findByIdAndMembersId(Long id, Long membersId);

    List<Commission> findByMembers(Members members);

}
