package com.example.demo_back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class SupportReceiver {
    @NotBlank(message = "User name can't be empty")
    private String username;
    @NotBlank(message = "Password can't be empty")
    private String password;
}
