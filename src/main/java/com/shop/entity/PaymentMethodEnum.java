package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethodEnum {
    @JsonProperty("Momo")
    MOMO("Momo"),
    @JsonProperty("VNPay")
    VNPAY("VNPay"),
    @JsonProperty("Cash")
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

