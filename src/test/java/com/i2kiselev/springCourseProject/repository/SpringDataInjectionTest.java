package com.i2kiselev.springCourseProject.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SpringDataInjectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AbstractProductRepository abstractProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RollSetRepository rollSetRepository;

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void dataSourceIsNotNull(){
        assertThat(dataSource).isNotNull();
    }
    @Test
    void userRepoIsNotNull(){
        assertThat(userRepository).isNotNull();
    }
    @Test
    void orderRepoIsNotNull(){
        assertThat(orderRepository).isNotNull();
    }
    @Test
    void abstractProductRepoIsNotNull(){
        assertThat(abstractProductRepository).isNotNull();
    }
    @Test
    void productRepoIsNotNull(){
        assertThat(productRepository).isNotNull();
    }
    @Test
    void rollRepoIsNotNull(){
        assertThat(rollRepository).isNotNull();
    }
    @Test
    void rollSetRepoIsNotNull(){
        assertThat(rollSetRepository).isNotNull();
    }
}
