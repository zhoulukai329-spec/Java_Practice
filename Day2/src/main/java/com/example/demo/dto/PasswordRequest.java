package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordRequest {
    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, max = 32, message = "password length must be between 6 and 32")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
