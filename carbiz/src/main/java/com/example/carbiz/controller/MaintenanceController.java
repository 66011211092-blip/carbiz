package com.example.carbiz.controller;

import com.example.carbiz.dto.MaintenanceCreateRequest;
import com.example.carbiz.entity.Maintenance;
import com.example.carbiz.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;
    @PreAuthorize("hasAnyRole('ADMIN','MAINTENANCE','MANAGER')")
    @GetMapping("/car/{carId}")
    public List<Maintenance> listByCar(@PathVariable Long carId) {
        return maintenanceService.getByCar(carId);
    }

    @PostMapping
    public ResponseEntity<Maintenance> add(@RequestBody MaintenanceCreateRequest req) {
        return ResponseEntity.ok(maintenanceService.add(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


