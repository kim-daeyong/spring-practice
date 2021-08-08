package com.example.springpractice.entity.dto;

import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.Product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderProductDto {
    private long id;

    private OrderDto orderDto;
    
    private ProductDto productDto;
}