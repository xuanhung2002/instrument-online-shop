package com.shop.service.impl;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.CartItem;
import com.shop.entity.Order;
import com.shop.repository.OrderRepository;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Converter converter;

    @Override
    public List<OrderResponseDTO> findOrdersByUsername(String username) {
        List<Order> orders = orderRepository.findOrdersByUsername(username);
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders
        ) {
            OrderResponseDTO orderResponseDTO = converter.toOrderResponseDTO(order);
            orderResponseDTOs.add(orderResponseDTO);
        }
        return orderResponseDTOs;
    }


    @Override
    public List<OrderResponseDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders
        ) {
            OrderResponseDTO orderResponseDTO = converter.toOrderResponseDTO(order);
            orderResponseDTOs.add(orderResponseDTO);
        }
        return orderResponseDTOs;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }


}
