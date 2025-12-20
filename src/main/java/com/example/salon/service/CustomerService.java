package com.example.salon.service;

import com.example.salon.dto.CustomerCreateRequest;
import com.example.salon.dto.CustomerResponse;
import com.example.salon.dto.CustomerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponse createProfile(CustomerCreateRequest request);

    CustomerResponse getMyProfile();

    CustomerResponse updateMyProfile(CustomerUpdateRequest request);

    CustomerResponse getCustomerById(Long id);

    Page<CustomerResponse> getAllCustomers(String search, Pageable pageable);
}
