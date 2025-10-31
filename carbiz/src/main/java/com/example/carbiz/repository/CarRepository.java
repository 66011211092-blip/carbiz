package com.example.carbiz.repository;

import com.example.carbiz.entity.Car;
import com.example.carbiz.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(CarStatus status);
    long countByStatus(CarStatus status);
}