package com.example.salon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public String profile() {
        return "Customer profile";
    }
}
