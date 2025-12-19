package com.example.salon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @GetMapping("/tasks")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public String tasks() {
        return "Employee tasks";
    }
}
