package com.example.carbiz.service;

import com.example.carbiz.dto.*;
import com.example.carbiz.repository.ReportRepository;
import com.example.carbiz.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepo;
    private final MaintenanceRepository maintenanceRepo;

    public ReportOverviewResponse overview(LocalDate start, LocalDate end) {
        Object[] row = reportRepo.overview(start, end);
        return new ReportOverviewResponse(
                ((Number) row[0]).longValue(),
                (BigDecimal) row[1],
                (BigDecimal) row[2]
        );
    }

    public List<DailySalesRow> daily(LocalDate start, LocalDate end) {
        return reportRepo.daily(start, end).stream()
                .map(r -> new DailySalesRow(
                        (LocalDate) r[0],
                        ((Number) r[1]).longValue(),
                        (BigDecimal) r[2],
                        (BigDecimal) r[3]
                ))
                .toList();
    }

    public List<MonthlySalesRow> monthly(Integer year) {
        return reportRepo.monthly(year).stream()
                .map(r -> new MonthlySalesRow(
                        ((Number) r[0]).intValue(),
                        ((Number) r[1]).intValue(),
                        ((Number) r[2]).longValue(),
                        (BigDecimal) r[3],
                        (BigDecimal) r[4]
                ))
                .toList();
    }

    public List<TopCarRow> topCars(LocalDate start, LocalDate end, int limit) {
        return reportRepo.topCars(start, end, PageRequest.of(0, limit)).stream()
                .map(r -> new TopCarRow(
                        ((Number) r[0]).longValue(),
                        ((Number) r[1]).longValue(),
                        (BigDecimal) r[2],
                        (BigDecimal) r[3]
                ))
                .toList();
    }

    public List<TopCustomerRow> topCustomers(LocalDate start, LocalDate end, int limit) {
        return reportRepo.topCustomers(start, end, PageRequest.of(0, limit)).stream()
                .map(r -> new TopCustomerRow(
                        (String) r[0],
                        ((Number) r[1]).longValue(),
                        (BigDecimal) r[2],
                        (BigDecimal) r[3]
                ))
                .toList();
    }

    public List<Map<String, Object>> paymentMethods(LocalDate start, LocalDate end) {
        return reportRepo.paymentMethods(start, end).stream()
                .map(r -> Map.of(
                        "method", r[0],
                        "deals", ((Number) r[1]).longValue(),
                        "revenue", r[2]
                ))
                .toList();
    }

    public double totalMaintenanceCostByCar(Long carId) {
        return maintenanceRepo.findByCar_Id(carId).stream()
                .map(m -> m.getCost() != null ? m.getCost().doubleValue() : 0.0)
                .reduce(0.0, Double::sum);
    }
}
