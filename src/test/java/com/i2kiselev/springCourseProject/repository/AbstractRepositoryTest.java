package com.i2kiselev.springCourseProject.repository;


import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AbstractRepositoryTest {

    @Autowired
    private AbstractProductRepository abstractProductRepository;

    @Test
    void findById(){
        AbstractProduct test = new Product();
        test.setName("testProduct");
        abstractProductRepository.save(test);
        AbstractProduct product = abstractProductRepository.findById(test.getId()).get();
        assertThat(product.getName()).isEqualTo("testProduct");
    }

    @Test
    void findAll(){
        //setup
        AbstractProduct test1 = new Product();
        test1.setName("testProduct1");
        AbstractProduct test2 = new Product();
        test2.setName("testProduct2");
        abstractProductRepository.save(test1);
        abstractProductRepository.save(test2);

        //act
        Iterable<AbstractProduct> abstractProducts = abstractProductRepository.findAll();
        //verify
        List<AbstractProduct> abstractProductList = new ArrayList<>();
        abstractProducts.forEach(abstractProductList::add);
        assertThat(abstractProductList.get(0).getName()).isEqualTo("testProduct1");
        assertThat(abstractProductList.get(1).getName()).isEqualTo("testProduct2");
    }

}
