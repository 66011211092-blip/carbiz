package com.example.carbiz.dto;

import java.time.LocalDateTime;

/**
 * ใช้เป็น Response มาตรฐานของทุก API
 * ตัวอย่าง Response:
 * {
 *   "success": true,
 *   "message": "Created successfully",
 *   "data": {...},
 *   "timestamp": "2025-10-31T22:10:15"
 * }
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "success", data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "created", data, LocalDateTime.now());
    }

    public static ApiResponse<?> fail(String msg) {
        return new ApiResponse<>(false, msg, null, LocalDateTime.now());
    }
}
