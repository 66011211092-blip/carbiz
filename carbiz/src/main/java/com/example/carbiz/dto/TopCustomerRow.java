package com.example.carbiz.dto;

import java.math.BigDecimal;

public record TopCustomerRow(
        String customerName,
        long deals,
        BigDecimal revenue,
        BigDecimal profit
) {}
