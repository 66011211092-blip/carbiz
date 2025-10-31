package com.example.carbiz.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResponse(
        Long id,
        Long carId,
        String carVin,
        String customerName,
        String customerPhone,
        LocalDate saleDate,
        BigDecimal finalPrice,
        String paymentMethod,
        BigDecimal profit
) {}
