package com.example.springpractice.entity.dto;

import java.util.List;
import java.util.Set;

import com.example.springpractice.entity.OrderProduct;
import com.example.springpractice.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    
    private long productId;
    
    private String name;

    private long count;

    private Set<OrderProduct> orderProducts;

    public Product toProduct() {
        return Product.builder()
                        .productId(this.productId)
                        .name(this.name)
                        .count(this.count)
                        .build();
    }
}