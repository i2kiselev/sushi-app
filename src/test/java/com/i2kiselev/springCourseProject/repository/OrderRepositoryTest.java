package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Order;
import com.i2kiselev.springCourseProject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void findAllByStatusIsNot_emptyResult(){
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        orderRepository.save(order);

        //act
        Page<Order> orderPage  = orderRepository.findAllByStatusIsNot(Order.Status.ACCEPTED, Pageable.unpaged());

        //verify
        assertThat(orderPage.getTotalElements()).isEqualTo(0);
    }

    @Test
    void findAllByStatusIsNot_positive(){
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.ACCEPTED);
        orderRepository.save(order);

        //act
        Page<Order> orderPage  = orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged());

        //verify
        assertEquals(1, orderPage.getTotalElements());
    }

    @Test
    void findAllByStatusIsNot_onlyNegatedType(){
        //setup
        Order order = new Order();
        order.setStatus(Order.Status.FINISHED);
        Order order2 = new Order();
        order2.setStatus(Order.Status.FINISHED);
        orderRepository.save(order);
        orderRepository.save(order2);

        //act
        Page<Order> orderPage  = orderRepository.findAllByStatusIsNot(Order.Status.FINISHED, Pageable.unpaged());

        //verify
        assertTrue(orderPage.toList().isEmpty());
    }

    @Test
    void findAllByUser_positive(){
        //setup
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);

        Order userOrder = new Order();
        userOrder.setUser(user);
        userOrder.setStatus(Order.Status.ACCEPTED);

        Order orderWithoutUser = new Order();

        orderRepository.save(userOrder);
        orderRepository.save(orderWithoutUser);

        //act
        Page<Order> orderPage = orderRepository.findAllByUser(user,Pageable.unpaged());

        //verify
        assertEquals(1, orderPage.getTotalElements());
        Order result = orderPage.toList().get(0);
        assertSame(userOrder, result);
    }

    @Test
    void findAllByUser_emptyResult(){
        //setup
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);
        Order order = new Order(Order.Status.ACCEPTED, user);
        orderRepository.save(order);
        User testUser = new User( "user2");
        userRepository.save(testUser);

        //act
        Page<Order> orderPage = orderRepository.findAllByUser(testUser,Pageable.unpaged());

        //verify
        assertTrue(orderPage.toList().isEmpty());
    }
}
