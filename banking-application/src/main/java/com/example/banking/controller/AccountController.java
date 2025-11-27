package com.example.banking.controller;

import com.example.banking.dto.*;
import com.example.banking.service.BankingService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final BankingService service;

    public AccountController(BankingService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public AccountDto create(@Valid @RequestBody CreateAccountRequest req) {
        var acc = service.createAccount(req.ownerName());
        return AccountDto.from(acc);
    }

    @GetMapping("/{accNo}/balance")
    public BalanceDto balance(@PathVariable String accNo) {
        return new BalanceDto(service.getBalance(accNo));
    }

    @PostMapping("/{accNo}/deposit")
    public void deposit(@PathVariable String accNo, @Valid @RequestBody AmountDto dto) {
        service.deposit(accNo, dto.amount());
    }

    @PostMapping("/{accNo}/withdraw")
    public void withdraw(@PathVariable String accNo, @Valid @RequestBody AmountDto dto) {
        service.withdraw(accNo, dto.amount());
    }

    @GetMapping("/{accNo}/transactions")
    public List<TransactionDto> list(@PathVariable String accNo) {
        return TransactionDto.fromList(service.transactions(accNo));
    }
}
