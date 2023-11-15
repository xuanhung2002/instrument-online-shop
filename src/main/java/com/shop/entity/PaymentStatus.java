package com.shop.entity;

import com.shop.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_status")
public class PaymentStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name ="status")
  private PaymentStatusEnum paymentStatusEnum;

  @OneToMany(mappedBy = "paymentStatus")
  private List<Order> orders;
}
