package com.shop.service;

import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.DetailItemOrder;
import com.shop.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getOrdersByUsername(String username);
    List<OrderResponseDTO> getAll();
    Order save(Order order);

    BigDecimal calcTotalAmount(List<DetailItemOrder> detailItemOrder);

    Order addNewOrder(String username, OrderRequestDTO orderRequestDTO);

}
