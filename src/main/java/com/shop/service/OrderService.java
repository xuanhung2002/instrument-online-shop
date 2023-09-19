package com.shop.service;

import com.shop.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersByUsername(String username);
    List<Order> findAll();
}
