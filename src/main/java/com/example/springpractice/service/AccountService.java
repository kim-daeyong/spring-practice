package com.example.springpractice.service;

import javax.transaction.Transactional;

import com.example.springpractice.entity.Account;
import com.example.springpractice.repository.AccountRepository;

import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public long deposit(long accountId, long amount) {
        Account account = accountRepository.findByAccountId(accountId);

        long currentAmount = account.getBalance() + amount;

        account.setBalance(currentAmount);
        accountRepository.save(account);

        return currentAmount;
    }
}