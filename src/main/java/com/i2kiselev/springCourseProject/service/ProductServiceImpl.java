package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.repository.AbstractProductRepository;
import com.i2kiselev.springCourseProject.repository.ProductRepository;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final RollRepository rollRepository;
    private final RollSetRepository rollSetRepository;
    private final AbstractProductRepository abstractProductRepository;

    public ProductServiceImpl(ProductRepository productRepository, RollRepository rollRepository, RollSetRepository rollSetRepository, AbstractProductRepository abstractProductRepository) {
        this.productRepository = productRepository;
        this.rollRepository = rollRepository;
        this.rollSetRepository = rollSetRepository;
        this.abstractProductRepository = abstractProductRepository;
    }

    @Override
    public AbstractProduct findById(Long id) {
        return abstractProductRepository.findById(id).get();
    }

    @Override
    public void saveRoll(Roll roll) {
        rollRepository.save(roll);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void saveRollSet(RollSet rollSet) {
        rollSetRepository.save(rollSet);
    }

    @Override
    public Iterable<AbstractProduct> findAll() {
        return abstractProductRepository.findAll();
    }

    @Override
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Iterable<Roll> findAllRolls() {
        return rollRepository.findAll();
    }

    @Override
    public Iterable<RollSet> findAllRollSets() {
        return rollSetRepository.findAll();
    }
}
