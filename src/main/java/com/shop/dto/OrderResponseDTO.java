package com.shop.dto;

import com.shop.entity.DetailItemOrder;
import com.shop.entity.OrderStatus;
import com.shop.entity.PaymentMethodEnum;
import com.shop.entity.PaymentStatusEnum;
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
    private OrderStatus orderStatus;
    private List<DetailItemOrder> detailItemOrders;


}
