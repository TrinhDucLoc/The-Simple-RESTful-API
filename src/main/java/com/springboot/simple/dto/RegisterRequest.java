package com.springboot.simple.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank/null")
    private String username;

    @NotBlank(message = "Email cannot be blank/null")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank/null")
    private String password;
}
