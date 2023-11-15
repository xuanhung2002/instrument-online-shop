package com.shop.controller;

import com.shop.converter.Converter;
import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.Order;
import com.shop.enums.OrderStatusEnum;
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
  public ResponseEntity<?> getAllOrders() {
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getAll().stream().map(converter::toOrderResponseDTO).toList());
  }

  @GetMapping("")
  public ResponseEntity<List<OrderResponseDTO>> getOrdersOfUser(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUsername(username).stream().map(converter::toOrderResponseDTO).toList());
  }

  @PostMapping("/add")
  public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                                   Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    Order newOrder = orderService.addNewOrder(username, orderRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(converter.toOrderResponseDTO(newOrder));
  }

  @PutMapping("/userCancelOrder/{id}")
  public ResponseEntity<?> deleteOrder(@PathVariable Integer id, Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    try {
      orderService.userCancelOrder(id, username);
      return ResponseEntity.status(HttpStatus.OK).body("Cancel success");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    }
  }
  @PutMapping("updateOrderStatus/{id}")
  public ResponseEntity<?> updateStatusOrder(@PathVariable Integer id, @RequestParam("newOrderStatus") String newStatus) {
    orderService.updateOrderStatus(id, newStatus);
    return ResponseEntity.status(HttpStatus.OK).body("OKKK");
  }

}
