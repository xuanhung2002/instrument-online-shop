package com.shop.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
    @JsonProperty("Momo")
    MOMO("Momo"),
    @JsonProperty("VNPay")
    VNPAY("VNPay"),
    @JsonProperty("Cash")
    CASH("Cash")
    ;

    private final String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

}

