package com.example.banking.dto;

import com.example.banking.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record TransactionDto(String type, BigDecimal amount, BigDecimal balanceAfter, String timestamp) {
    public static TransactionDto from(Transaction t) {
        return new TransactionDto(t.getType().name(), t.getAmount(), t.getBalanceAfter(), t.getTimestamp().toString());
    }

    public static List<TransactionDto> fromList(List<Transaction> list) {
        return list.stream().map(TransactionDto::from).toList();
    }
}
