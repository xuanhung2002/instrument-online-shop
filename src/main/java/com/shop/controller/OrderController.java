package com.shop.controller;

import com.shop.converter.Converter;
import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.Order;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    Converter converter;

    //need authorize admin ==> handle later
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersOfUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return new ResponseEntity<>(orderService.getOrdersByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                                     Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Order newOrder = orderService.addNewOrder(username, orderRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(converter.toOrderResponseDTO(newOrder));
    }

}
