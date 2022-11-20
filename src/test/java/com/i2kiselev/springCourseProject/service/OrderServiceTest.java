package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.exception.NoEntityException;
import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    @Spy
    private OrderService orderService;

    @Test
    void findByIdWhenOptionalIsPresent() {
        final Order order = new Order();
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));
        assertThat(orderService.findById(1L)).isEqualTo(order);
    }

    @Test
    void findByIdWhenOptionalIsNotPresent() {
        given(orderRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(NoEntityException.class, () -> orderService.findById(1L));
    }

    @Test
    void getUnfinishedOrdersWhenResultIsEmpty() {
        given(orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged())).willReturn(Page.empty());
        assertThat(orderService.getUnfinishedOrders(Pageable.unpaged()).getTotalElements()).isEqualTo(0);
    }

    @Test
    void getUnfinishedOrders() {
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        Page<Order> page = new PageImpl<>(Collections.singletonList(order));
        given(orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged())).willReturn(page);
        //act
        Page<Order> result = orderService.getUnfinishedOrders(Pageable.unpaged());
        //verify
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertSame(order, result.toList().get(0));
    }

    @Test
    void getOrdersForCurrentUserWhenResultIsEmpty() {
        //setup
        User user = new User();
        given(userService.getCurrentUser()).willReturn(user);
        given(orderRepository.findAllByUser(user, Pageable.unpaged())).willReturn(Page.empty());
        //act&&verify
        assertThat(orderService.getOrdersForCurrentUser(Pageable.unpaged()).getTotalElements()).isEqualTo(0);
    }

    @Test
    void getOrdersForCurrentUser() {
        //setup
        User user = new User();
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        Page<Order> page = new PageImpl<>(Collections.singletonList(order));
        given(userService.getCurrentUser()).willReturn(user);
        given(orderRepository.findAllByUser(user, Pageable.unpaged())).willReturn(page);
        //act
        Page<Order> ordersForCurrentUser = orderService.getOrdersForCurrentUser(Pageable.unpaged());
        //verify
        assertThat(ordersForCurrentUser.getTotalElements()).isEqualTo(1);
        assertSame(order, ordersForCurrentUser.toList().get(0));
    }

    @Test
    void nextStatus_orderIsNotFinished() {
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        doReturn(order).when(orderService).findById(1L);
        //act
        orderService.nextStatus(1L);
        //verify
        assertThat(order.getStatus()).isEqualTo(Order.Status.IN_KITCHEN);
    }

    @Test
    void nextStatus_orderIsFinished() {
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.FINISHED);
        doReturn(order).when(orderService).findById(1L);
        //act
        orderService.nextStatus(1L);
        //verify
        assertThat(order.getStatus()).isEqualTo(Order.Status.FINISHED);
    }

    @Test
    void setTotal() {
        //setup
        Order order = new Order();
        List<AbstractProduct> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setPrice(100);
        Product product2 = new Product();
        product2.setPrice(50);
        products.add(product1);
        products.add(product2);
        order.setItems(products);
        //act
        orderService.setTotal(order);
        //verify
        assertThat(order.getTotal()).isEqualTo(150);
    }
}
