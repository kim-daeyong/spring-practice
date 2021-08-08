package com.example.springpractice.repository;

import javax.persistence.LockModeType;

import com.example.springpractice.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Account findByAccountId(long accountId);

    // @Lock(LockModeType.OPTIMISTIC)
    // public Account findByAccountId(long accountId);
}