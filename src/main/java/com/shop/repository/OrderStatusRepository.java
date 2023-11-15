package com.shop.repository;

import com.shop.entity.OrderStatus;
import com.shop.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
  OrderStatus getOrderStatusByOrderStatusEnum(OrderStatusEnum orderStatusEnum);
  boolean existsByOrderStatusEnum(OrderStatusEnum orderStatusEnum);
}
