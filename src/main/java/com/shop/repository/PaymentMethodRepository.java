package com.shop.repository;

import com.shop.entity.PaymentMethod;
import com.shop.enums.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
  PaymentMethod getPaymentMethodByPaymentMethodEnum(PaymentMethodEnum paymentMethodEnum);
  boolean existsByPaymentMethodEnum(PaymentMethodEnum paymentMethodEnum);
}
