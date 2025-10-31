package com.example.carbiz.controller;

import com.example.carbiz.dto.SaleCreateRequest;
import com.example.carbiz.entity.Sale;
import com.example.carbiz.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PreAuthorize("hasAnyRole('ADMIN','SALES','MANAGER')")

    @GetMapping
    public List<Sale> list() { return saleService.getAll(); }

    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody SaleCreateRequest req) {
        return ResponseEntity.ok(saleService.create(req));
    }


}



