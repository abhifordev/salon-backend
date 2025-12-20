package com.example.salon.controller;

import com.example.salon.dto.*;
import com.example.salon.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // CUSTOMER → create own profile
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/profile")
    public CustomerResponse createProfile(
            @Valid @RequestBody CustomerCreateRequest request) {
        return customerService.createProfile(request);
    }

    // CUSTOMER → view own profile
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/profile")
    public CustomerResponse myProfile() {
        return customerService.getMyProfile();
    }

    // CUSTOMER → update own profile
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/profile")
    public CustomerResponse updateProfile(
            @Valid @RequestBody CustomerUpdateRequest request) {
        return customerService.updateMyProfile(request);
    }

    // ADMIN → view any customer
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // ADMIN → list customers (pagination + search)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<CustomerResponse> listCustomers(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return customerService.getAllCustomers(search, pageable);
    }
}
