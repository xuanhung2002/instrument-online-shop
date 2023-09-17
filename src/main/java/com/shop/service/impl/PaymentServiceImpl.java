package com.shop.service.impl;

import com.shop.entity.Order;
import com.shop.entity.PaymentMethodEnum;
import com.shop.entity.PaymentStatusEnum;
import com.shop.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean processPayment(Order order, PaymentMethodEnum paymentMethod) {
        boolean paymentSuccess = false;

        switch (paymentMethod) {
            case MOMO:
                // Thực hiện thanh toán bằng Momo
                // ...
                break;
            case STRIPE:
                // Thực hiện thanh toán bằng Stripe
                // ...
                break;
            case PAYPAL:
                // Thực hiện thanh toán bằng PayPal
                // ...
                break;
            case CASH:
                // Thực hiện thanh toán bằng PayPal
                // ...
                break;
        }

        if (paymentSuccess) {
            order.setPaymentStatus(PaymentStatusEnum.PAID);
            // Cập nhật trạng thái thanh toán
        }

        return paymentSuccess;
    }
}
