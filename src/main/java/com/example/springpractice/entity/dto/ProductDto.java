package com.example.springpractice.entity.dto;

import com.example.springpractice.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    
    private long productId;
    
    private String name;

    private long count;

    public Product toProduct() {
        return Product.builder()
                        .productId(this.productId)
                        .name(this.name)
                        .count(this.count)
                        .build();
    }
}