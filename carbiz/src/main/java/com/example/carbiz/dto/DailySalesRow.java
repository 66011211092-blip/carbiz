package com.example.carbiz.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailySalesRow(
        LocalDate date,
        long deals,
        BigDecimal revenue,
        BigDecimal profit
) {}
