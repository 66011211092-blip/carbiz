package com.example.carbiz.dto;

import java.math.BigDecimal;

public record TopCarRow(
        Long carId,
        long deals,
        BigDecimal revenue,
        BigDecimal profit
) {}
