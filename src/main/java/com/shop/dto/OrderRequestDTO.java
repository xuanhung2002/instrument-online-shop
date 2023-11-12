package com.shop.dto;

import com.shop.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private String customerName;

    private String customerPhone;

    private String address;

    private String paymentMethod;

    private String paymentStatus;

    private List<Integer> idCartItems;

}
