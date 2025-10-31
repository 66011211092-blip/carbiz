package com.example.carbiz.dto;

import java.math.BigDecimal;

public record ReportOverviewResponse(
        long deals,
        BigDecimal revenue,
        BigDecimal profit
) {}
