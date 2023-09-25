package com.shop.service;

import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> findOrdersByUsername(String username);
    List<OrderResponseDTO> findAll();

    Order save(Order order);
}
