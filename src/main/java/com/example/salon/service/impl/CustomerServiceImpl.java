package com.example.salon.service.impl;

import com.example.salon.dto.*;
import com.example.salon.entity.Customer;
import com.example.salon.entity.User;
import com.example.salon.exception.BadRequestException;
import com.example.salon.exception.ResourceNotFoundException;
import com.example.salon.repository.CustomerRepository;
import com.example.salon.repository.UserRepository;
import com.example.salon.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public CustomerResponse createProfile(CustomerCreateRequest request) {

        User user = getCurrentUser();

        if (customerRepository.findByUser(user).isPresent()) {
            throw new BadRequestException("Customer profile already exists");
        }

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setGender(request.getGender());

        return map(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse getMyProfile() {

        User user = getCurrentUser();

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() ->
                        ResourceNotFoundException.forField(
                                "Customer",
                                "user",
                                user.getEmail()
                        )
                );

        return map(customer);
    }

    @Override
    public CustomerResponse updateMyProfile(CustomerUpdateRequest request) {

        User user = getCurrentUser();

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() ->
                        ResourceNotFoundException.forField(
                                "Customer",
                                "user",
                                user.getEmail()
                        )
                );

        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setGender(request.getGender());

        return map(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        ResourceNotFoundException.forId("Customer", id)
                );

        return map(customer);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(String search, Pageable pageable) {

        Page<Customer> page;

        if (search == null || search.isBlank()) {
            page = customerRepository.findAll(pageable);
        } else {
            page = customerRepository.findByFullNameContainingIgnoreCase(search, pageable);
        }

        return page.map(this::map);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        ResourceNotFoundException.forField(
                                "User",
                                "email",
                                email
                        )
                );
    }

    private CustomerResponse map(Customer customer) {

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setEmail(customer.getUser().getEmail());
        response.setFullName(customer.getFullName());
        response.setPhone(customer.getPhone());
        response.setGender(customer.getGender());
        return response;
    }
}
