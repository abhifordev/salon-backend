package com.example.salon.service;

import com.example.salon.dto.CustomerCreateRequest;
import com.example.salon.dto.CustomerResponse;

public interface CustomerService {

    CustomerResponse createProfile(CustomerCreateRequest request);

    CustomerResponse getMyProfile();

    CustomerResponse getCustomerById(Long id);
}
