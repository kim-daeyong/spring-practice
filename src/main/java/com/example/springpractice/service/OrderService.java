package com.example.springpractice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityManager;

import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.OrderProduct;
import com.example.springpractice.entity.Product;
import com.example.springpractice.repository.OrderProductRepository;
import com.example.springpractice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productservice;

    private final OrderProductRepository orderProductRepository;

    private final EntityManager em;

    public OrderService(OrderRepository orderRepository, 
                            ProductService productservice, 
                            OrderProductRepository orderProductRepository,
                            EntityManager em) {
        this.orderRepository = orderRepository;
        this.productservice = productservice;
        this.orderProductRepository = orderProductRepository;
        this.em = em;
    }

    @Transactional(readOnly = true)
    public Order getOrders(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public long addOrders(long productId) {

        Order order = Order.builder().name("test").build();

        System.out.println("Delegate 1: "+ em.getDelegate());

        Product product = productservice.orderProduct(productId);

        System.out.println("product class 1 : "+ em.find(Product.class, 1l));

        orderRepository.save(order);

        OrderProduct orderProduct = OrderProduct.builder().order(order).product(product).build();

        order.setOrderProducts(Set.of(orderProduct));
        product.setOrderProducts(Set.of(orderProduct));

        orderProductRepository.save(orderProduct);

        return order.getOrderId();
    }
    
}