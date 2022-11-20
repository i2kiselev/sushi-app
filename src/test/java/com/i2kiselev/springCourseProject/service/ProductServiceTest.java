package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.repository.AbstractProductRepository;
import com.i2kiselev.springCourseProject.repository.ProductRepository;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private RollRepository rollRepository;
    @Mock
    private RollSetRepository rollSetRepository;
    @Mock
    private AbstractProductRepository abstractProductRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAll() {
        //setup
        List<AbstractProduct> productList = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        Roll roll = new Roll();
        roll.setId(2L);
        productList.add(product);
        productList.add(roll);
        given(abstractProductRepository.findAll()).willReturn(productList);
        //act
        Iterable<AbstractProduct> result = productService.findAll();
        //verify
        assertThat(result).contains(product);
        assertThat(result).contains(roll);
    }

    @Test
    void findAllProducts() {
        //setup
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        Product product2 = new Product();
        product.setId(2L);
        productList.add(product);
        productList.add(product2);
        given(productRepository.findAll()).willReturn(productList);
        //act
        Iterable<Product> result = productService.findAllProducts();
        //verify
        assertThat(result).contains(product);
        assertThat(result).contains(product2);
    }

    @Test
    void findAllRolls() {
        //setup
        List<Roll> productList = new ArrayList<>();
        Roll roll = new Roll();
        roll.setId(1L);
        Roll roll2 = new Roll();
        roll2.setId(2L);
        productList.add(roll);
        productList.add(roll2);
        given(rollRepository.findAll()).willReturn(productList);
        //act
        Iterable<Roll> result = productService.findAllRolls();
        //verify
        assertThat(result).contains(roll2);
        assertThat(result).contains(roll);
    }

    @Test
    void findAllRollSets() {
        //setup
        List<RollSet> rollSets = new ArrayList<>();
        RollSet rollSet = new RollSet();
        rollSet.setId(1L);
        RollSet rollSet1 = new RollSet();
        rollSet1.setId(2L);
        rollSets.add(rollSet);
        rollSets.add(rollSet1);
        given(rollSetRepository.findAll()).willReturn(rollSets);
        //act
        Iterable<RollSet> result = productService.findAllRollSets();
        //verify
        assertThat(result).contains(rollSet1);
        assertThat(result).contains(rollSet);
    }

    @Test
    void findAllRollSetsByStaff() {
        //setup
        List<RollSet> rollSets = new ArrayList<>();
        RollSet rollSet = new RollSet();
        rollSet.setId(1L);
        RollSet rollSet1 = new RollSet();
        rollSet1.setId(2L);
        rollSets.add(rollSet);
        rollSets.add(rollSet1);
        User user = new User();
        given(rollSetRepository.findAllByStaff()).willReturn(rollSets);
        //act
        Iterable<RollSet> result = productService.findAllRollSetsByStaff();
        //verify
        assertThat(result).contains(rollSet1);
        assertThat(result).contains(rollSet);
    }

    @Test
    void findAllRollSetsByCurrentUser() {
        //setup
        List<RollSet> rollSets = new ArrayList<>();
        RollSet rollSet = new RollSet();
        rollSet.setId(1L);
        RollSet rollSet1 = new RollSet();
        rollSet1.setId(2L);
        rollSets.add(rollSet);
        rollSets.add(rollSet1);
        User user = new User();
        given(userService.getCurrentUser()).willReturn(user);
        given(rollSetRepository.findAllByStaff()).willReturn(rollSets);
        //act
        Iterable<RollSet> result = productService.findAllRollSetsByStaff();
        //verify
        assertThat(result).contains(rollSet1);
        assertThat(result).contains(rollSet);
    }

}
