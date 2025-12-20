package com.example.salon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String gender;
}
