package com.shop.service.impl;

import com.shop.converter.Converter;
import com.shop.dto.OrderRequestDTO;
import com.shop.entity.DetailItemOrder;
import com.shop.entity.Order;
import com.shop.entity.OrderStatus;
import com.shop.enums.OrderStatusEnum;
import com.shop.enums.PaymentMethodEnum;
import com.shop.enums.PaymentStatusEnum;
import com.shop.repository.OrderRepository;
import com.shop.repository.OrderStatusRepository;
import com.shop.repository.PaymentMethodRepository;
import com.shop.repository.PaymentStatusRepository;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.utils.MapJsonEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    PaymentStatusRepository paymentStatusRepository;
    @Autowired
    EntityManager entityManager;


    @Override
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.getOrdersByUsername(username);

    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
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
        order.setPaymentMethod(paymentMethodRepository.getPaymentMethodByPaymentMethodEnum(PaymentMethodEnum.valueOf(orderRequestDTO.getPaymentMethod().toUpperCase())));
        order.setPaymentStatus(paymentStatusRepository.getPaymentStatusByPaymentStatusEnum(PaymentStatusEnum.valueOf(orderRequestDTO.getPaymentStatus().toUpperCase())));
        order.setOrderStatus(orderStatusRepository.getOrderStatusByOrderStatusEnum(OrderStatusEnum.PROCESSING));
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
    @Transactional
    @Override
    public void userCancelOrder(Integer id, String username) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if (order.getOrderStatus().getOrderStatusEnum() == OrderStatusEnum.PROCESSING &&
                    order.getUser().getAccount().getUsername().equals(username)) {
                order.setOrderStatus(getOrderStatusByEnum(OrderStatusEnum.CANCELLED));
                orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("Unable to cancel order. Either the order is not in PROCESSING state or it does not belong to the specified user.");
            }
        } else {
            throw new EntityNotFoundException("This order is not existed");
        }
    }


    @Override
    public void updateOrderStatus(Integer orderId, String newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setOrderStatus(getOrderStatusByEnum(OrderStatusEnum.valueOf(newStatus)));
            orderRepository.save(order);
        } else {
            throw new EntityNotFoundException("This order is not existed");
        }
    }

    @Override
    public OrderStatus getOrderStatusByEnum(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.getOrderStatusByOrderStatusEnum(orderStatusEnum);
    }

}
