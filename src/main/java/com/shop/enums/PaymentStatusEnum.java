package com.shop.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentStatusEnum {
    @JsonProperty("Paid")
    PAID("Paid"),
    @JsonProperty("Pending")
    PENDING("Pending"),
    @JsonProperty("Failed")
    FAILED("Failed"),
    @JsonProperty("Cancelled")
    CANCELLED("Cancelled");

    private String displayName;

    PaymentStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    }

