package com.example.springpractice.service;

import java.util.List;
import java.util.NoSuchElementException;


import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.OrderProduct;
import com.example.springpractice.entity.Product;
import com.example.springpractice.repository.OrderProductRepository;
import com.example.springpractice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productservice;

    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, 
                            ProductService productservice, 
                            OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productservice = productservice;
        this.orderProductRepository = orderProductRepository;
    }

    @Transactional(readOnly = true)
    public Order getOrders(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    // @Transactional
    public long addOrders(long productId) {

        Order order = Order.builder().name("test").build();
        Product product = productservice.orderProduct(productId);
        OrderProduct orderProduct = OrderProduct.builder().order(order).product(product).build();

        order.setOrderProducts(List.of(orderProduct));
        product.setOrderProducts(List.of(orderProduct));

        orderProductRepository.save(orderProduct);
        orderRepository.save(order);

        return order.getOrderId();
    }
    
}