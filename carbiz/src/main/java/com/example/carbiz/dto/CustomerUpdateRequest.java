package com.example.carbiz.dto;

public record CustomerUpdateRequest(
        String firstName,
        String lastName,
        String phone,
        String email,
        String nationalId,
        String address
) {}
