package com.example.banking.service;

import com.example.banking.entity.Account;
import com.example.banking.entity.Transaction;
import com.example.banking.entity.TransactionType;
import com.example.banking.exception.BadRequest;
import com.example.banking.exception.ResourceNotFound;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public BankingService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    @Transactional
    public Account createAccount(String ownerName) {
        Account acc = new Account();
        acc.setOwnerName(ownerName);
        acc.setAccountNumber("AC" + System.currentTimeMillis());
        return accountRepo.save(acc);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accNo) {
        return findAccount(accNo).getBalance();
    }

    @Transactional
    public void deposit(String accNo, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BadRequest("Amount must be greater than 0");

        Account acc = findAccount(accNo);
        acc.setBalance(acc.getBalance().add(amount));
        accountRepo.save(acc);

        Transaction tx = new Transaction();
        tx.setAccount(acc);
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(amount);
        tx.setBalanceAfter(acc.getBalance());
        txRepo.save(tx);
    }

    @Transactional
    public void withdraw(String accNo, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BadRequest("Amount must be greater than 0");

        Account acc = findAccount(accNo);
        if (acc.getBalance().compareTo(amount) < 0)
            throw new BadRequest("Insufficient balance");

        acc.setBalance(acc.getBalance().subtract(amount));
        accountRepo.save(acc);

        Transaction tx = new Transaction();
        tx.setAccount(acc);
        tx.setType(TransactionType.WITHDRAW);
        tx.setAmount(amount);
        tx.setBalanceAfter(acc.getBalance());
        txRepo.save(tx);
    }

    @Transactional(readOnly = true)
    public List<Transaction> transactions(String accNo) {
        return txRepo.findByAccountOrderByTimestampDesc(findAccount(accNo));
    }

    private Account findAccount(String accNo) {
        return accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new ResourceNotFound("Account not found: " + accNo));
    }
}
