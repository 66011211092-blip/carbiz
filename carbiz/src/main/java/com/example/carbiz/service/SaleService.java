package com.example.carbiz.service;

import com.example.carbiz.dto.SaleCreateRequest;
import com.example.carbiz.entity.Car;
import com.example.carbiz.entity.CarStatus;
import com.example.carbiz.entity.Customer;
import com.example.carbiz.entity.Sale;
import com.example.carbiz.repository.CarRepository;
import com.example.carbiz.repository.CustomerRepository;
import com.example.carbiz.repository.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService {

    private final SaleRepository saleRepo;
    private final CarRepository carRepo;
    private final CustomerRepository customerRepo;

    @Transactional
    public Sale create(SaleCreateRequest req) {
        log.info("[SALE] Creating sale for carId={}, customerId={}", req.carId(), req.customerId());

        Car car = carRepo.findById(req.carId())
                .orElseThrow(() -> new IllegalStateException("Car not found with id=" + req.carId()));
        if (car.getStatus() == CarStatus.SOLD) {
            throw new IllegalStateException("Car already sold");
        }

        Customer customer = customerRepo.findById(req.customerId())
                .orElseThrow(() -> new IllegalStateException("Customer not found with id=" + req.customerId()));

        // ✅ อัปเดตสถานะรถเป็น SOLD
        car.setStatus(CarStatus.SOLD);
        carRepo.save(car);

        // ✅ ใช้วันที่จาก request ถ้ามี, ถ้าไม่มีใช้วันที่ปัจจุบัน
        LocalDate saleDate = (req.saleDate() != null) ? req.saleDate() : LocalDate.now();

        Sale sale = Sale.builder()
                .car(car)
                .customer(customer)
                .customerName(customer.getFirstName() + " " + customer.getLastName())
                .customerPhone(customer.getPhone())
                .saleDate(saleDate)
                .finalPrice(req.finalPrice())
                .paymentMethod(req.paymentMethod())
                .build();

        Sale saved = saleRepo.save(sale);
        log.info("[SALE] Sale created successfully id={}", saved.getId());
        return saved;
    }

    public java.util.List<Sale> getAll() {
        return saleRepo.findAll();
    }
}
