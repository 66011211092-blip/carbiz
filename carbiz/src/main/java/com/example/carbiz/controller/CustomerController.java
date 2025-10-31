package com.example.carbiz.controller;

import com.example.carbiz.dto.CustomerCreateRequest;
import com.example.carbiz.entity.Customer;
import com.example.carbiz.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> list() { return customerService.getAll(); }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) { return customerService.get(id); }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerCreateRequest req) {
        return ResponseEntity.ok(customerService.create(req));
    }
    @PreAuthorize("hasAnyRole('ADMIN','SALES','MANAGER')")
    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody CustomerCreateRequest req) {
        return customerService.update(id, req);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}




