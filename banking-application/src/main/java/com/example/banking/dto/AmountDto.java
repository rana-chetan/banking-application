package com.example.banking.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AmountDto(@NotNull @DecimalMin("0.01") BigDecimal amount) {}
