package com.example.springpractice.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.springpractice.entity.Account;
import com.example.springpractice.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AccountServiceTest {

    private static final ExecutorService service =
            Executors.newFixedThreadPool(100);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private long accountId;

    @BeforeEach
    public void setUp() {
        Account account = Account.builder().name("계좌").build();
        account = accountRepository.save(account);
        accountId = account.getAccountId();

    }

    @Test
    public void deposit_Concurrency_NonLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(100);
        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                accountService.depositNonLock(accountId, 10);
                latch.countDown();
            });
        }
        latch.await();
        Account account = accountRepository.findById(accountId).orElseThrow();
        assertThat(account.getBalance()).isEqualTo(1000);

    }

    @Test
    public void deposit_Concurrency_Lock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(100);
        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                accountService.depositLock(accountId, 10);
                latch.countDown();
            });
        }
        latch.await();
        Account account = accountRepository.findById(accountId).orElseThrow();
        assertThat(account.getBalance()).isEqualTo(1000);

    }
}