package com.example.carbiz.service;

import com.example.carbiz.dto.CarCreateRequest;
import com.example.carbiz.dto.CarUpdateStatusRequest;
import com.example.carbiz.entity.Car;
import com.example.carbiz.entity.CarStatus;
import com.example.carbiz.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepo;

    public Car create(CarCreateRequest req) {
        Car car = Car.builder()
                .brand(req.brand())
                .model(req.model())
                .year(req.year())
                .licensePlate(req.licensePlate())
                .vin(req.vin())
                .mileage(req.mileage())
                .purchasePrice(req.purchasePrice())
                .status(CarStatus.RECEIVED) // default status
                .build();
        return carRepo.save(car);
    }

    public Car setStatus(Long id, CarUpdateStatusRequest req) {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        car.setStatus(req.status());
        return carRepo.save(car);
    }
}