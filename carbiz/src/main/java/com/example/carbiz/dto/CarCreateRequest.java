package com.example.carbiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CarCreateRequest(
        @NotBlank String brand,
        String model,
        Integer year,
        String licensePlate,
        String vin,
        Integer mileage,
        @NotNull BigDecimal purchasePrice
) {}