package com.shop.service.impl;

import com.shop.converter.Converter;
import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.DetailItemOrder;
import com.shop.entity.Order;
import com.shop.entity.OrderStatus;
import com.shop.repository.OrderRepository;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.utils.MapJsonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Converter converter;
    @Autowired
    UserService userService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;

    @Override
    public List<OrderResponseDTO> getOrdersByUsername(String username) {
        List<Order> orders = orderRepository.getOrdersByUsername(username);
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
        for (Order order : orders
        ) {
            OrderResponseDTO orderResponseDTO = converter.toOrderResponseDTO(order);
            orderResponseDTOs.add(orderResponseDTO);
        }
        return orderResponseDTOs;
    }


    @Override
    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(converter::toOrderResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public BigDecimal calcTotalAmount(List<DetailItemOrder> detailItemOrder) {
        return detailItemOrder.stream()
                .map(c -> BigDecimal.valueOf(c.getItem().getPrice()).multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    @Override
    public Order addNewOrder(String username, OrderRequestDTO orderRequestDTO) {

        Order order = new Order();
        order.setUser(userService.getUserByUsername(username));
        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setCustomerPhone(orderRequestDTO.getCustomerPhone());
        order.setOrderDate(LocalDateTime.now());
        order.setAddress(orderRequestDTO.getAddress());
        order.setPaymentMethod(MapJsonEnum.mapPaymentMethod(orderRequestDTO.getPaymentMethod()));
        order.setPaymentStatus(MapJsonEnum.mapPaymentStatus(orderRequestDTO.getPaymentStatus()));
        order.setOrderStatus(OrderStatus.PROCESSING);

        List<DetailItemOrder> detailItemOrders = new ArrayList<>();

        for (Integer idCartItem : orderRequestDTO.getIdCartItems()
        ) {
            DetailItemOrder detailItemOrder = new DetailItemOrder();
            detailItemOrder.setOrder(order);
            detailItemOrder.setItem(cartItemService.getItemByCartItemId(idCartItem));
            detailItemOrder.setQuantity(cartItemService.getCartItemById(idCartItem).getQuantity());
            detailItemOrders.add(detailItemOrder);
            cartItemService.deleteById(idCartItem);
        }
        order.setDetailItemOrders(detailItemOrders);
        order.setTotalAmount(calcTotalAmount(detailItemOrders));

        return save(order);

    }

}
