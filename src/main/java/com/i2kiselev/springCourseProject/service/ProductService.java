package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
    AbstractProduct findById(Long id);
    Iterable<AbstractProduct>findAll();
    Iterable<Product>findAllProducts();
    Iterable<Roll>findAllRolls();
    Iterable<RollSet>findAllRollSets();
    void saveProduct(Product product);
    void saveRoll(Roll roll);
    void saveRollSet(RollSet rollSet);
}
