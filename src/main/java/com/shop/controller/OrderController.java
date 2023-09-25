package com.shop.controller;

import com.shop.converter.Converter;
import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.*;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.UserService;
import com.shop.utils.MapJsonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    Converter converter;
    @Autowired
    CartService cartService;
    //need authorize admin ==> handle later
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersOfUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return new ResponseEntity<>(orderService.findOrdersByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Order order = new Order();
        order.setUser(userService.getUserByUsername(username));
        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setCustomerPhone(orderRequestDTO.getCustomerPhone());
        order.setOrderDate(LocalDateTime.now());
        order.setAddress(orderRequestDTO.getAddress());
        order.setTotalAmount(orderRequestDTO.getTotalAmount());
        order.setPaymentMethod(MapJsonEnum.mapPaymentMethod(orderRequestDTO.getPaymentMethod()));
        order.setPaymentStatus(MapJsonEnum.mapPaymentStatus(orderRequestDTO.getPaymentStatus()));
        order.setOrderStatus(OrderStatus.PROCESSING);

        List<DetailItemOrder> detailItemOrders = new ArrayList<>();

        for (Integer idCartItem: orderRequestDTO.getIdCartItems()
             ) {
            DetailItemOrder detailItemOrder = new DetailItemOrder();
            detailItemOrder.setOrder(order);
            detailItemOrder.setItem(cartItemService.findItemByCartItemId(idCartItem));
            detailItemOrder.setQuantity(cartItemService.findCartItemById(idCartItem).getQuantity());

            detailItemOrders.add(detailItemOrder);

            cartItemService.deleteById(idCartItem);

        }
        order.setDetailItemOrders(detailItemOrders);

        Order savedOrder = orderService.save(order);

        return new ResponseEntity<>(converter.toOrderResponseDTO(savedOrder), HttpStatus.OK);
    }

}
