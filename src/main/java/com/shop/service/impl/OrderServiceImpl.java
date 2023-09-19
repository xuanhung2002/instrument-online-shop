package com.shop.service.impl;

import com.shop.entity.Order;
import com.shop.repository.OrderRepository;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findOrdersByUsername(username);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
