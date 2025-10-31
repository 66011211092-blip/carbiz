package com.example.carbiz.dto;

import com.example.carbiz.entity.Role;

public record RegisterRequest(String username, String password, Role role,
                              String fullname, String contact) {}