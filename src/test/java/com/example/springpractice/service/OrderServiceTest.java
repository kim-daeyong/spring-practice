package com.example.springpractice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;

import com.example.springpractice.entity.Order;
import com.example.springpractice.entity.OrderProduct;
import com.example.springpractice.entity.Product;
import com.example.springpractice.entity.dto.OrderDto;
import com.example.springpractice.entity.dto.OrderProductDto;
import com.example.springpractice.repository.OrderProductRepository;
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
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private EntityManager em;

    private long productId;

    @BeforeEach
    public void setUp() {
        Product product = Product.builder().productId(1l).name("yami").count(10001).build();
        productRepository.save(product);
        productId = product.getProductId();
    }

    @Test
    @Transactional
    public void addOrderTest() throws InterruptedException {

        long orderId = orderService.addOrders(productId);

        Order order = orderService.getOrders(orderId);

        OrderProduct orderProduct = orderProductService.getOrderProduct(productId);

        long temp = order.getOrderProducts().stream().findFirst().get().getProduct().getCount();

        long count = productService.getProduct(productId).getCount();

        assertThat(order).isNotNull();
        assertThat(count).isEqualTo(10000);

    }

    @Test
    public void addOrderTest_Concurrency() throws InterruptedException {

        System.out.println("start : " + em);

        CountDownLatch latch = new CountDownLatch(100);
        for (int i=0; i < 100; i++) {
            service.execute(() -> {
                orderService.addOrders(productId);
                latch.countDown();
            });
        }
        latch.await();

        OrderProduct orderProduct = orderProductService.getOrderProduct(100l);

        Order order = orderService.getOrders(100l);

        long count = productService.getProduct(productId).getCount();

        assertThat(count).isEqualTo(9901l);

    }

    @Test
    @Transactional
    public void addOrderTest_for() throws InterruptedException {

        for (int i=0; i < 100; i++) {
            orderService.addOrders(productId);
        }

        OrderProduct orderProduct = orderProductService.getOrderProduct(100l);

        OrderProductDto dto = orderProduct.toOrderProductDto();

        Order order = orderService.getOrders(100l);

        long temp = orderProduct.toOrderProductDto().getProductDto().getCount();

        long count = productService.getProduct(productId).getCount();

        assertThat(count).isEqualTo(9901l);

    }

    @Test
    public void deleteOrderTest() throws InterruptedException {
        orderRepository.deleteAll();

    }

    @AfterEach
    public void afterEach() {
        // productRepository.deleteAll();
        // orderRepository.deleteAll();
    }

}