package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.exception.NoEntityException;
import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.OrderRepository;
import com.i2kiselev.springCourseProject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void findByIdWhenOptionalIsPresent(){
        final Order order = new Order();
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));
        assertThat(orderService.findById(1L)).isEqualTo(order);
    }

    @Test
    void findByIdWhenOptionalIsNotPresent(){
        final Order order = new Order();
        given(orderRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(NoEntityException.class,()->orderService.findById(1L));
    }

    @Test
    void getUnfinishedOrdersWhenResultIsEmpty(){
        given(orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged())).willReturn(Page.empty());
        assertThat(orderService.getUnfinishedOrders(Pageable.unpaged()).getTotalElements()).isEqualTo(0);
    }

    @Test
    void getUnfinishedOrders(){
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        Page<Order> page = new PageImpl<Order>(Collections.singletonList(order));
        given(orderRepository.findAllByStatusIsNot(Order.Status.FINISHED,Pageable.unpaged())).willReturn(page);
        assertThat(orderService.getUnfinishedOrders(Pageable.unpaged()).getTotalElements()).isEqualTo(1);
    }

    @Test
    void getOrdersForCurrentUserWhenResultIsEmpty(){
        User user = new User();
        given(userService.getCurrentUser()).willReturn(user);
        given(orderRepository.findAllByUser(user, Pageable.unpaged())).willReturn(Page.empty());
        assertThat(orderService.getOrdersForCurrentUser(Pageable.unpaged()).getTotalElements()).isEqualTo(0);
    }

    @Test
    void getOrdersForCurrentUser(){
        User user = new User();
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        Page<Order> page = new PageImpl<Order>(Collections.singletonList(order));
        given(userService.getCurrentUser()).willReturn(user);
        given(orderRepository.findAllByUser(user,Pageable.unpaged())).willReturn(page);
        assertThat(orderService.getOrdersForCurrentUser(Pageable.unpaged()).getTotalElements()).isEqualTo(1);
    }

    @Test
    void nextStatusWhenOrderIsNotFinished(){
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        doReturn(order).when(orderService).findById(1L);
        orderService.nextStatus(1L);
        System.out.println(order.getStatus());
        assertThat(order.getStatus()).isEqualTo(Order.Status.IN_KITCHEN);
    }

    @Test
    void nextStatusWhenOrderIsFinished(){
        Order order = new Order();
        order.setStatus(Order.Status.FINISHED);
        doReturn(order).when(orderService).findById(1L);
        orderService.nextStatus(1L);
        System.out.println(order.getStatus());
        assertThat(order.getStatus()).isEqualTo(Order.Status.FINISHED);
    }

    @Test
    void setTotal(){
        Order order = new Order();
        List<AbstractProduct> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setPrice(100);
        Product product2 = new Product();
        product2.setPrice(50);
        products.add(product1);
        products.add(product2);
        order.setItems(products);
        orderService.setTotal(order);
        assertThat(order.getTotal()).isEqualTo(150);
    }
}
