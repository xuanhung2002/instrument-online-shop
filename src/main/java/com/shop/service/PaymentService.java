package com.shop.service;

import com.shop.entity.Order;
import com.shop.entity.PaymentMethodEnum;

public interface PaymentService {
    boolean processPayment(Order order, PaymentMethodEnum paymentMethod);
}
