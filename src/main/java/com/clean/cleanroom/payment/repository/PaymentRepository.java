package com.clean.cleanroom.payment.repository;

import com.clean.cleanroom.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.imp_uid = :imp_uid")
    Optional<PaymentEntity> findByImpUid(@Param("imp_uid") String imp_uid);
    @Query("SELECT COUNT(p) > 0 FROM PaymentEntity p WHERE p.merchant_uid = :merchantUid")
    boolean existsByMerchantUid(@Param("merchantUid") String merchantUid);
}
