package com.example.springpractice.service;

import com.example.springpractice.repository.StockRepository;

import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
}