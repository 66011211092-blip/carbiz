package com.example.carbiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;  // ต้องมี field นี้

    @Column(name = "customer_name", length = 200)
    private String customerName;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "final_price", precision = 15, scale = 2)
    private BigDecimal finalPrice;

    @Column(name = "payment_method", length = 20)
    private String paymentMethod;
}