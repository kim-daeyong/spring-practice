package com.example.springpractice.repository;

import com.example.springpractice.entity.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    
}