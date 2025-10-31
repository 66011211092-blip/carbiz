package com.example.carbiz.service;

import com.example.carbiz.dto.MaintenanceCreateRequest;
import com.example.carbiz.entity.Car;
import com.example.carbiz.entity.CarStatus;
import com.example.carbiz.entity.Maintenance;
import com.example.carbiz.repository.CarRepository;
import com.example.carbiz.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final CarRepository carRepository;

    public List<Maintenance> getByCar(Long carId) {
        return maintenanceRepository.findByCar_Id(carId);
    }

    public Maintenance add(MaintenanceCreateRequest req) {
        log.info("[MAINT] Adding maintenance for carId={}", req.carId());

        Car car = carRepository.findById(req.carId())
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + req.carId()));

        // ✅ ถ้ารถขายแล้ว ห้ามเพิ่ม maintenance
        if (car.getStatus() == CarStatus.SOLD) {
            throw new IllegalStateException("Cannot add maintenance to a SOLD car");
        }

        Maintenance m = Maintenance.builder()
                .car(car)
                .detail(req.detail())
                .date(req.date())
                .cost(req.cost())
                .build();

        Maintenance saved = maintenanceRepository.save(m);
        log.info("[MAINT] Maintenance added successfully id={}", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        maintenanceRepository.deleteById(id);
        log.info("[MAINT] Deleted maintenance id={}", id);
    }
}
