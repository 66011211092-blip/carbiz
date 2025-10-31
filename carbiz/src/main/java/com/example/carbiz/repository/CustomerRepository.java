package com.example.carbiz.repository;

import com.example.carbiz.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "(:q IS NULL OR " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.phone) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%')))")
    Page<Customer> search(@Param("q") String q, Pageable pageable);
    boolean existsByPhone(String phone);

    // เพิ่ม method นี้
    boolean existsByEmail(String email);

    // เพิ่ม method นี้
    boolean existsByNationalId(String nationalId);

}