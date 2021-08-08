package com.example.springpractice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.Product;
import com.example.springpractice.entity.dto.OrderDto;
import com.example.springpractice.repository.OrderRepository;
import com.example.springpractice.repository.ProductRepository;

@SpringBootTest
public class OrderServiceTest {

    private static final ExecutorService service = Executors.newFixedThreadPool(100);
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    private long productId;

    @BeforeEach
    public void setUp() {
        Product product = Product.builder().productId(1l).name("yami").count(10001).build();
        productRepository.save(product);
        productId = product.getProductId();
    }

    @Test
    // @Transactional
    public void addOrderTest() throws InterruptedException {

        long orderId = orderService.addOrders(productId);

        Order order = orderService.getOrders(orderId);

        long count = productService.getProduct(productId).getCount();

        assertThat(order).isNotNull();
        assertThat(count).isEqualTo(10000);

    }

    @Test
    @Transactional
    public void addOrderTest_Concurrency() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(100);
        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                orderService.addOrders(productId);
                latch.countDown();
            });
        }
        latch.await();

        long count = productService.getProduct(productId).getCount();

        assertThat(count).isEqualTo(990);

    }

    @Test
    public void deleteOrderTest() throws InterruptedException {
        orderRepository.deleteAll();

    }

    @AfterEach
    public void afterEach() {
        // productRepository.deleteAll();
        orderRepository.deleteAll();
    }

}