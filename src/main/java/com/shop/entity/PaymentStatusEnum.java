package com.shop.entity;

public enum PaymentStatusEnum {
    PAID("Paid"),
    PENDING("Pending"),
    FAILED("Failed"),
    CANCELLED("Cancelled");
    // Thêm các trạng thái thanh toán khác (nếu cần)

    private String displayName;

    PaymentStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    }

