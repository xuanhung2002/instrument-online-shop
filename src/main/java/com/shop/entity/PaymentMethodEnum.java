package com.shop.entity;

public enum PaymentMethodEnum {
    MOMO("Momo"),
    STRIPE("Stripe"),
    PAYPAL("PayPal"),
    CASH("Cash")
    ;
    // Thêm các phương thức thanh toán khác (nếu cần)

    private String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    }

