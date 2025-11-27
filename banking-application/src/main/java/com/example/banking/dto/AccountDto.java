package com.example.banking.dto;

import com.example.banking.entity.Account;

import java.math.BigDecimal;

public record AccountDto(String accountNumber, String ownerName, BigDecimal balance) {

    public static AccountDto from(Account a) {
        return new AccountDto(a.getAccountNumber(), a.getOwnerName(), a.getBalance());
    }
}
