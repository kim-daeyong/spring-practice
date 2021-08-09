package com.example.springpractice.entity.dto;

import java.util.List;
import java.util.Set;

import com.example.springpractice.entity.OrderProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private long orderId;
    
    private String name;
    
    private Set<OrderProduct> orderProducts;
}