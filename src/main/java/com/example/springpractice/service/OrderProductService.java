package com.example.springpractice.service;

import java.util.NoSuchElementException;

import com.example.springpractice.entity.OrderProduct;
import com.example.springpractice.repository.OrderProductRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }


    public OrderProduct getOrderProduct(long id) {
        return  orderProductRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
}