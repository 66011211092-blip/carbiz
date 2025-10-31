package com.example.carbiz.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceCreateRequest(
        Long carId,
        String detail,
        BigDecimal cost,
        LocalDate date
) {}