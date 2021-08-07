package com.example.springpractice.service;

import java.util.List;
import java.util.NoSuchElementException;


import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.Product;
import com.example.springpractice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productservice;

    public OrderService(OrderRepository orderRepository, ProductService productservice) {
        this.orderRepository = orderRepository;
        this.productservice = productservice;
    }

    static long index = 0;

    @Transactional(readOnly = true)
    public Order getOrders(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    public long addOrders(long productId) {

        Order order = Order.builder().orderId(index).name("test").build();

        Product product = productservice.orderProduct(productId);

        order.setProducts(List.of(product));

        orderRepository.save(order);

        index++;

        return order.getOrderId();
    }
    
}