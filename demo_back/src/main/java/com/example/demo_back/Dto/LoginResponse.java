package com.example.demo_back.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean success;
    private String message;
    private Integer id;
}
