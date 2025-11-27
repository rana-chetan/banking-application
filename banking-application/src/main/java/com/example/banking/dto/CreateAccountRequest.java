package com.example.banking.dto;

import jakarta.validation.constraints.*;

public record CreateAccountRequest(@NotBlank String ownerName) {

}
