package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
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
class RollRepositoryTest {
    @Autowired
    private RollRepository rollRepository;

    @Test
    void findById(){
        Roll test = new Roll();
        test.setName("testRoll");
        rollRepository.save(test);
        Roll product = rollRepository.findById(test.getId()).get();
        assertThat(product.getName()).isEqualTo("testRoll");
    }

    @Test
    void findAll(){
        Roll test1 = new Roll();
        test1.setName("testRoll1");
        Roll test2 = new Roll();
        test2.setName("testRoll2");
        rollRepository.save(test1);
        rollRepository.save(test2);
        Iterable<Roll> products = rollRepository.findAll();
        List<Roll> productList = new ArrayList<>();
        products.forEach(productList::add);
        assertThat(productList.get(0).getName()).isEqualTo("testRoll1");
        assertThat(productList.get(1).getName()).isEqualTo("testRoll2");
    }
}
