package com.example.salon.controller;

import com.example.salon.dto.CustomerCreateRequest;
import com.example.salon.dto.CustomerResponse;
import com.example.salon.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    // ADMIN → view any customer
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}




//package com.example.salon.controller;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/customer")
//public class CustomerController {
//
//    @GetMapping("/profile")
//    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
//    public String profile() {
//        return "Customer profile";
//    }
//}
