package com.example.springpractice.repository;

import com.example.springpractice.entity.OrderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    
}