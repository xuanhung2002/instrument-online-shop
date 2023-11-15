package com.shop.dto;

import com.shop.entity.DetailItemOrder;
import com.shop.enums.OrderStatusEnum;
import com.shop.enums.PaymentMethodEnum;
import com.shop.enums.PaymentStatusEnum;
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
public class OrderResponseDTO {

    private Integer id;
    private String customerName;
    private String customerPhone;
    private LocalDateTime orderDate;
    private String address;
    private BigDecimal totalAmount;
    private PaymentMethodEnum paymentMethod;
    private PaymentStatusEnum paymentStatus;
    private OrderStatusEnum orderStatus;
    private List<DetailItemOrder> detailItemOrders;


}
