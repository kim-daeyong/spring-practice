package com.example.springpractice.service;

import java.util.NoSuchElementException;

import com.example.springpractice.entity.Product;
import com.example.springpractice.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Transactional
    public Product orderProduct(long id) {
        Product product = getProduct(id);

        product.setCount(product.getCount() - 1);

        productRepository.save(product);

        return product;
    }
}