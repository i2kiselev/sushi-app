package com.i2kiselev.springCourseProject.service.integration;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.OrderRepository;
import com.i2kiselev.springCourseProject.repository.UserRepository;
import com.i2kiselev.springCourseProject.service.ProductService;
import com.i2kiselev.springCourseProject.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShoppingCartServiceIntegrationTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_USER = "test_user";

    @BeforeEach
    void init(){
        User user = new User(TEST_USER);
        userRepository.save(user);
    }

    @WithUserDetails(value = TEST_USER, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Transactional
    @Test
    void cartCheckout(){
        //setup
        Product product = new Product();
        product.setPrice(150);
        productService.saveProduct(product);
        shoppingCartService.addProduct(product);

        Product product2 = new Product();
        product2.setPrice(25);
        productService.saveProduct(product2);
        shoppingCartService.addProduct(product2);

        //act
        shoppingCartService.checkout();

        //verify
        assertTrue(shoppingCartService.getProductsInCart().isEmpty());

        Iterable<Order> orderIterable = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        orderIterable.forEach(orders::add);

        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(Order.Status.ACCEPTED, order.getStatus());
        assertEquals(175, order.getTotal());

        List<AbstractProduct> items = order.getItems();
        assertEquals(2, items.size());
        assertEquals(product, items.get(0));
        assertEquals(product2, items.get(1));

        assertEquals("test_user", order.getUser().getUsername());
    }
}


