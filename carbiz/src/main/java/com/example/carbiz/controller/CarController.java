package com.example.carbiz.controller;

import com.example.carbiz.dto.CarCreateRequest;
import com.example.carbiz.dto.CarUpdateStatusRequest;
import com.example.carbiz.entity.Car;
import com.example.carbiz.entity.CarStatus;
import com.example.carbiz.repository.CarRepository;
import com.example.carbiz.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarRepository carRepo;

    @PostMapping
    public ResponseEntity<Car> create(@Valid @RequestBody CarCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(req));
    }

    @GetMapping
    public List<Car> list(@RequestParam(required = false) CarStatus status) {
        return status == null ? carRepo.findAll() : carRepo.findByStatus(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> get(@PathVariable Long id) {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return ResponseEntity.ok(car);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Car> updateStatus(@PathVariable Long id, @Valid @RequestBody CarUpdateStatusRequest req) {
        Car updatedCar = carService.setStatus(id, req);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!carRepo.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepo.deleteById(id);
    }


}
