package com.example.carbiz.repository;

import com.example.carbiz.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByCar_Id(Long carId);
}
