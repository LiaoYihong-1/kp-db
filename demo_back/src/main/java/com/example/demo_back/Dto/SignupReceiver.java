package com.example.demo_back.dto;

import com.example.demo_back.dao.enums.Gender;
import lombok.Data;

@Data
public class SignupReceiver {
    private String username;
    private Gender gender;
    private String password;
    private Integer age;
    private String email;
    private String phone;
}
