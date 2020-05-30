package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.repository.AbstractProductRepository;
import com.i2kiselev.springCourseProject.repository.ProductRepository;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RollRepository rollRepository;
    private final RollSetRepository rollSetRepository;
    private final AbstractProductRepository abstractProductRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, RollRepository rollRepository, RollSetRepository rollSetRepository, AbstractProductRepository abstractProductRepository) {
        this.productRepository = productRepository;
        this.rollRepository = rollRepository;
        this.rollSetRepository = rollSetRepository;
        this.abstractProductRepository = abstractProductRepository;
    }

    public AbstractProduct findById(Long id) {
        return abstractProductRepository.findById(id).get();
    }

    public void saveProduct(Product product) { productRepository.save(product);    }
    public void saveRoll(Roll roll) {
        rollRepository.save(roll);
    }

    public void saveRollSet(RollSet rollSet) {
        rollSetRepository.save(rollSet);
    }

    public Iterable<AbstractProduct> findAll() {
        return abstractProductRepository.findAll();
    }

    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Iterable<Roll> findAllRolls() {
        return rollRepository.findAll();
    }

    public Iterable<RollSet> findAllRollSets() {
        return rollSetRepository.findAll();
    }

    public void saveImage(AbstractProduct product) {
        try {
            product.setImage(product.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
