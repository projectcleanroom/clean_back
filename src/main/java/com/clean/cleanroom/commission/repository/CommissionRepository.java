package com.clean.cleanroom.commission.repository;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.members.entity.Members;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    @EntityGraph(attributePaths = {"members", "address", "estimates"}) //@EntityGraph를 사용하여 Members와 Address, estimates를 미리 로드
    Optional<List<Commission>> findByMembersId(Long membersId);

    Optional<Commission> findByIdAndMembersId(Long id, Long membersId);

    List<Commission> findByMembers(Members members);

    Commission findByEstimatesIdAndId(Long estimateId, Long commissionId);

    // 특정 회원이 생성한 가장 최근의 청소의뢰를 가져오는 기능
    Optional<Commission> findTopByMembersIdOrderByIdDesc(Long membersId);

}
