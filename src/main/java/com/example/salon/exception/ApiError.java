package com.example.salon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
