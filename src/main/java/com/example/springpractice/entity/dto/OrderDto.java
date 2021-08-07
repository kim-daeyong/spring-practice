package com.example.springpractice.entity.dto;

import java.util.List;

import com.example.springpractice.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private long orderId;
    
    private String name;
    
    private List<ProductDto> products;
}