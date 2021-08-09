package com.example.springpractice.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import com.example.springpractice.entity.Account;
import com.example.springpractice.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<Product> findByProductId(long productId);
    
}