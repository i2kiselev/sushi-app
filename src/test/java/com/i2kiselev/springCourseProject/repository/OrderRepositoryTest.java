package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.User;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void findById(){
        Order order = new Order();
        orderRepository.save(order);
        Order orderTest = orderRepository.findById(order.getId()).get();
        assertThat(orderTest.getId()).isEqualTo(order.getId());
    }

    @Test
    void emptyFindAllByStatusIsNot(){
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        orderRepository.save(order);
        Page<Order> orderPage  = orderRepository.findAllByStatusIsNot(Order.Status.ACCEPTED, Pageable.unpaged());
        assertThat(orderPage.getTotalElements()).isEqualTo(0);
    }

    @Test
    void notEmptyFindAllByStatusIsNot(){
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        orderRepository.save(order);
        Page<Order> orderPage  = orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged());
        assertThat(orderPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void notEmptyFindAllByUser(){
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.ACCEPTED);
        orderRepository.save(order);
        Page<Order> orderPage = orderRepository.findAllByUser(user,Pageable.unpaged());
        assertThat(orderPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    void emptyFindAllByUser(){
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);
        Order order = new Order(Order.Status.ACCEPTED, user);
        orderRepository.save(order);
        User testUser = new User( "user2");
        userRepository.save(testUser);
        Page<Order> orderPage = orderRepository.findAllByUser(testUser,Pageable.unpaged());
        assertThat(orderPage.getTotalElements()).isEqualTo(0);
    }
}
