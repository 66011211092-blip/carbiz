package com.example.carbiz.service;

import com.example.carbiz.dto.CustomerCreateRequest;
import com.example.carbiz.entity.Customer;
import com.example.carbiz.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public Customer create(CustomerCreateRequest req) {
        // Validate required fields
        if (req.firstName() == null || req.firstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (req.lastName() == null || req.lastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        // Check duplicates
        if (req.phone() != null && customerRepository.existsByPhone(req.phone())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        if (req.email() != null && customerRepository.existsByEmail(req.email())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (req.nationalId() != null && customerRepository.existsByNationalId(req.nationalId())) {
            throw new DataIntegrityViolationException("National ID already exists");
        }

        Customer c = new Customer();
        c.setFirstName(req.firstName().trim());
        c.setLastName(req.lastName().trim());
        c.setPhone(req.phone() != null ? req.phone().trim() : null);
        c.setEmail(req.email() != null ? req.email().trim() : null);
        c.setNationalId(req.nationalId() != null ? req.nationalId().trim() : null);
        c.setAddress(req.address() != null ? req.address().trim() : null);

        return customerRepository.save(c);
    }

    public Customer update(Long id, CustomerCreateRequest req) {
        Customer c = get(id);

        // Check for duplicate phone
        if (req.phone() != null && !req.phone().equals(c.getPhone())
                && customerRepository.existsByPhone(req.phone())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Check for duplicate email
        if (req.email() != null && !req.email().equals(c.getEmail())
                && customerRepository.existsByEmail(req.email())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        // Check for duplicate national ID
        if (req.nationalId() != null && !req.nationalId().equals(c.getNationalId())
                && customerRepository.existsByNationalId(req.nationalId())) {
            throw new DataIntegrityViolationException("National ID already exists");
        }

        if (req.firstName() != null) c.setFirstName(req.firstName().trim());
        if (req.lastName() != null) c.setLastName(req.lastName().trim());
        if (req.phone() != null) c.setPhone(req.phone().trim());
        if (req.email() != null) c.setEmail(req.email().trim());
        if (req.nationalId() != null) c.setNationalId(req.nationalId().trim());
        if (req.address() != null) c.setAddress(req.address().trim());

        return customerRepository.save(c);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}