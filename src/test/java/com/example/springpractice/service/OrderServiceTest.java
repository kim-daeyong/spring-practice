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

    @BeforeEach
    public void setUp() {
        Product product = Product.builder().productId(1l).name("yami").count(10001).build();
        productRepository.save(product);
    }

    @Test
    @Transactional
    public void addOrderTest() throws InterruptedException {

        long productId = 1l;

        long orderId = orderService.addOrders(productId);

        Order order = orderService.getOrders(orderId);

        long count = order.getProducts().get(0).getCount();

        assertThat(order).isNotNull();
        assertThat(count).isEqualTo(10000);

    }

    @Test
    @Transactional
    public void addOrderTest_Thread() throws InterruptedException {

        long productId = 1l;

        long orderId = 99l;

        CountDownLatch latch = new CountDownLatch(100);
        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                orderService.addOrders(productId);
                latch.countDown();
            });
        }
        latch.await();

        Order order = orderService.getOrders(orderId);
        OrderDto orderDto = order.toOrderDto();
        long a = orderDto.getProducts().get(0).getCount();

        assertThat(order).isNotNull();
        assertThat(a).isEqualTo(990);

    }

    @Test
    public void deleteOrderTest() throws InterruptedException {
        long id = 1l;

        orderRepository.deleteById(id);

    }

    @Test
    public void deleteProductTest() throws InterruptedException {
        long id = 1l;

        productRepository.deleteById(id);

    }

    @AfterEach
    public void afterEach() {
        // productRepository.deleteAll();
        // orderRepository.deleteAll();
    }

}