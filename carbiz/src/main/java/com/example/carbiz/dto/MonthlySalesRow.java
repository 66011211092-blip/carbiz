package com.example.carbiz.dto;

import java.math.BigDecimal;

public record MonthlySalesRow(
        int year,
        int month,
        long deals,
        BigDecimal revenue,
        BigDecimal profit
) {}
