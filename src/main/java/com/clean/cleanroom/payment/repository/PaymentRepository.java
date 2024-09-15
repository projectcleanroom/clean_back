package com.clean.cleanroom.payment.repository;

import com.clean.cleanroom.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByImpUid(@Param("impUid") String impUid);
    boolean existsByMerchantUid(@Param("merchantUid") String merchantUid);
}
