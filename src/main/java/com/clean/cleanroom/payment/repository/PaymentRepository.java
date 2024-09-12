package com.clean.cleanroom.payment.repository;

import com.clean.cleanroom.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByImpUid(String impUid);
}