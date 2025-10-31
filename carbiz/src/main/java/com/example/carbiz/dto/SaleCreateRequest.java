package com.example.carbiz.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleCreateRequest(
        Long carId,
        Long customerId,
        String customerName,
        String customerPhone,
        LocalDate saleDate,
        BigDecimal finalPrice,
        String paymentMethod
) {}