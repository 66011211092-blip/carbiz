package com.example.carbiz.dto;

public record CustomerCreateRequest(
        String firstName,
        String lastName,
        String phone,
        String email,
        String nationalId,
        String address
) {}