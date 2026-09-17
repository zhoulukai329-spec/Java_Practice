package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MoneyRequest {
    @NotNull(message = "amount cannot be null")
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
