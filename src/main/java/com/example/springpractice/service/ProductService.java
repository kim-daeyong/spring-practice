package com.example.springpractice.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityManager;

import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.Product;
import com.example.springpractice.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    private final EntityManager em;

    // public ProductService(ProductRepository productRepository) {
    //     this.productRepository = productRepository;
    // }

    @Transactional(readOnly = true)
    public Product getProduct(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Transactional(readOnly = true)
    public Product getProductLock(long id) {
        System.out.println("Delegate 3: "+ em.getDelegate());
        return productRepository.findByProductId(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.REPEATABLE_READ)
    public Product orderProduct(long id) {

        Product product = getProductLock(id);

        System.out.println("Delegate 2: "+ em.getDelegate());
        System.out.println("product class 2 : "+ em.find(Product.class, 1l));

        product.setCount(product.getCount() - 1);

        return product;
    }
}