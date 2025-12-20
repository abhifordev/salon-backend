package com.example.salon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "\\d{10}")
    private String phone;

    private String gender;
}
