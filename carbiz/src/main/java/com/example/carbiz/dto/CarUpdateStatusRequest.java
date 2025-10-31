package com.example.carbiz.dto;

import com.example.carbiz.entity.CarStatus;
import jakarta.validation.constraints.NotNull;

public record CarUpdateStatusRequest(
        @NotNull(message = "Status is required")
        CarStatus status
) {}