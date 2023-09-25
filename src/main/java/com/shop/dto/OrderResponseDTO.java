package com.shop.dto;

import com.shop.entity.DetailItemOrder;
import com.shop.entity.OrderStatus;
import com.shop.entity.PaymentMethodEnum;
import com.shop.entity.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private Integer id;
    private String customerName;
    private String customerPhone;
    private LocalDateTime orderDate;
    private String address;
    private BigDecimal totalAmount;
    private PaymentMethodEnum paymentMethod;
    private PaymentStatusEnum paymentStatus;
    private OrderStatus orderStatus;
    private List<DetailItemOrder> detailItemOrders;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Integer id, String customerName, String customerPhone, LocalDateTime orderDate, String address, BigDecimal totalAmount, PaymentMethodEnum paymentMethod, PaymentStatusEnum paymentStatus, OrderStatus orderStatus, List<DetailItemOrder> detailItemOrders) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.orderDate = orderDate;
        this.address = address;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.detailItemOrders = detailItemOrders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<DetailItemOrder> getDetailItemOrders() {
        return detailItemOrders;
    }

    public void setDetailItemOrders(List<DetailItemOrder> detailItemOrders) {
        this.detailItemOrders = detailItemOrders;
    }
}
