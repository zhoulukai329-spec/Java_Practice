package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateCardRequest {
    @NotBlank(message = "studentId cannot be blank")
    @Size(min = 10, max = 10, message = "studentId must contain exactly 10 characters")
    private String studentId;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 2, max = 20, message = "name length must be between 2 and 20")
    private String name;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, max = 32, message = "password length must be between 6 and 32")
    private String password;

    @NotNull(message = "initialMoney cannot be null")
    @DecimalMin(value = "0.00", message = "initialMoney cannot be negative") // 花钱一定要有钱
    private BigDecimal initialMoney;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(BigDecimal initialMoney) {
        this.initialMoney = initialMoney;
    }
}
