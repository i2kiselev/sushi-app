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
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findById(){
        //setup
        Product test = new Product();
        test.setName("testProduct");
        productRepository.save(test);
        //act
        AbstractProduct product = productRepository.findById(test.getId()).get();
        //verify
        assertThat(product.getName()).isEqualTo("testProduct");
    }

    @Test
    void findAll(){
        //setup
        Product test1 = new Product();
        test1.setName("testProduct1");
        Product test2 = new Product();
        test2.setName("testProduct2");
        productRepository.save(test1);
        productRepository.save(test2);

        //act
        Iterable<Product> products = productRepository.findAll();

        //verify
        List<AbstractProduct> productList = new ArrayList<>();
        products.forEach(productList::add);
        assertThat(productList.get(0).getName()).isEqualTo("testProduct1");
        assertThat(productList.get(1).getName()).isEqualTo("testProduct2");
    }
}
