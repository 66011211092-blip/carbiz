package com.example.carbiz.repository;

import com.example.carbiz.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // ✅ ตรวจว่ารถนี้มีการขายแล้วหรือยัง
    boolean existsByCar_Id(Long carId);

    // ✅ ดึงข้อมูลตามช่วงวันที่ขาย
    List<Sale> findBySaleDateBetween(LocalDate start, LocalDate end);

    // ✅ ดึงข้อมูลการขายของรถแต่ละคัน
    Optional<Sale> findByCar_Id(Long carId);

    // ✅ ดึงข้อมูลการขายของลูกค้า
    List<Sale> findByCustomer_Id(Long customerId);

    // ✅ ค้นหาชื่อลูกค้า (ใช้กรณีค้นหาในระบบ)
    @Query("SELECT s FROM Sale s WHERE s.customerName LIKE %:name% ORDER BY s.saleDate DESC")
    List<Sale> findByCustomerNameContaining(@Param("name") String name);

    // ✅ ใช้กรณี report เท่านั้น (โหลด car มาด้วย)
    @Query("SELECT s FROM Sale s JOIN FETCH s.car ORDER BY s.saleDate DESC")
    List<Sale> findAllWithCar();
}
