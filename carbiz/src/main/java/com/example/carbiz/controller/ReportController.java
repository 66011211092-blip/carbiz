package com.example.carbiz.controller;

import com.example.carbiz.dto.*;
import com.example.carbiz.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // ✅ ปิดการตรวจสิทธิ์ชั่วคราว (ไม่มี PreAuthorize)
    @GetMapping("/overview")
    public ReportOverviewResponse overview(@RequestParam(required = false) String start,
                                           @RequestParam(required = false) String end) {
        LocalDate s = start != null ? LocalDate.parse(start) : null;
        LocalDate e = end != null ? LocalDate.parse(end) : null;
        return reportService.overview(s, e);
    }

    @GetMapping("/daily")
    public List<DailySalesRow> daily(@RequestParam(required = false) String start,
                                     @RequestParam(required = false) String end) {
        LocalDate s = start != null ? LocalDate.parse(start) : null;
        LocalDate e = end != null ? LocalDate.parse(end) : null;
        return reportService.daily(s, e);
    }

    @GetMapping("/monthly")
    public List<MonthlySalesRow> monthly(@RequestParam(required = false) Integer year) {
        return reportService.monthly(year);
    }

    @GetMapping("/top-cars")
    public List<TopCarRow> topCars(@RequestParam(required = false) String start,
                                   @RequestParam(required = false) String end,
                                   @RequestParam(defaultValue = "5") int limit) {
        LocalDate s = start != null ? LocalDate.parse(start) : null;
        LocalDate e = end != null ? LocalDate.parse(end) : null;
        return reportService.topCars(s, e, limit);
    }

    @GetMapping("/top-customers")
    public List<TopCustomerRow> topCustomers(@RequestParam(required = false) String start,
                                             @RequestParam(required = false) String end,
                                             @RequestParam(defaultValue = "5") int limit) {
        LocalDate s = start != null ? LocalDate.parse(start) : null;
        LocalDate e = end != null ? LocalDate.parse(end) : null;
        return reportService.topCustomers(s, e, limit);
    }

    @GetMapping("/payment-methods")
    public List<Map<String, Object>> paymentMethods(@RequestParam(required = false) String start,
                                                    @RequestParam(required = false) String end) {
        LocalDate s = start != null ? LocalDate.parse(start) : null;
        LocalDate e = end != null ? LocalDate.parse(end) : null;
        return reportService.paymentMethods(s, e);
    }

    @GetMapping("/maintenance/total-by-car/{carId}")
    public Map<String, Object> maintenanceTotal(@PathVariable Long carId) {
        double total = reportService.totalMaintenanceCostByCar(carId);
        return Map.of("carId", carId, "totalMaintenanceCost", total);
    }
}
