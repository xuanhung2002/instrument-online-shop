package com.shop.dto;

import com.shop.entity.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderRequestDTO {

    private String customerName;

    private String customerPhone;

    private String address;

    private BigDecimal totalAmount;

    private String paymentMethod;

    private String paymentStatus;

    private List<Integer> idCartItems;

    public OrderRequestDTO() {
    }


    public OrderRequestDTO(String customerName, String customerPhone, String address, BigDecimal totalAmount, String paymentMethod, String paymentStatus, List<Integer> idCartItems) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.idCartItems = idCartItems;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<Integer> getIdCartItems() {
        return idCartItems;
    }

    public void setIdCartItems(List<Integer> idCartItems) {
        this.idCartItems = idCartItems;
    }
}
