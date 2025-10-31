package com.example.carbiz.dto;

import java.math.BigDecimal;

public record PaymentMethodRow(
        String method,
        long deals,
        BigDecimal revenue
) {}
