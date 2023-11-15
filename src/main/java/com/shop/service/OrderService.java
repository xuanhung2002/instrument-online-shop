package com.shop.service;

import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.DetailItemOrder;
import com.shop.entity.Order;
import com.shop.entity.OrderStatus;
import com.shop.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> getOrdersByUsername(String username);
    List<Order> getAll();
    Order save(Order order);

    BigDecimal calcTotalAmount(List<DetailItemOrder> detailItemOrder);

    Order addNewOrder(String username, OrderRequestDTO orderRequestDTO);

    void userCancelOrder(Integer id, String username);

    void updateOrderStatus(Integer orderId, String newStatus);

    OrderStatus getOrderStatusByEnum(OrderStatusEnum orderStatusEnum);

}
