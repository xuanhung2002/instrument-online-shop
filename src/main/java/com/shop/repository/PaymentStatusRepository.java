package com.shop.repository;

import com.shop.entity.PaymentStatus;
import com.shop.enums.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer> {
  PaymentStatus getPaymentStatusByPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum);
  boolean existsByPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum);
}
