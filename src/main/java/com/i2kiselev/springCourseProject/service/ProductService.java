package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.*;
import com.i2kiselev.springCourseProject.repository.AbstractProductRepository;
import com.i2kiselev.springCourseProject.repository.ProductRepository;
import com.i2kiselev.springCourseProject.repository.RollRepository;
import com.i2kiselev.springCourseProject.repository.RollSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RollRepository rollRepository;
    private final RollSetRepository rollSetRepository;
    private final AbstractProductRepository abstractProductRepository;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, RollRepository rollRepository, RollSetRepository rollSetRepository, AbstractProductRepository abstractProductRepository, UserService userService) {
        this.productRepository = productRepository;
        this.rollRepository = rollRepository;
        this.rollSetRepository = rollSetRepository;
        this.abstractProductRepository = abstractProductRepository;
        this.userService = userService;
    }

    private Roll findRollById(Long id) {
        Optional<Roll> roll = rollRepository.findById(id);
        return roll.orElseThrow(NoSuchElementException::new);
    }

    public AbstractProduct findById(Long id) {
        Optional<AbstractProduct> abstractProduct = abstractProductRepository.findById(id);
        return abstractProduct.orElseThrow(NoSuchElementException::new);
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

    public Iterable<RollSet> findAllRollSetsByStaff(){ return rollSetRepository.findAllByStaff();}

    public Iterable<RollSet> findAllRollsetsByCurrentUser() { return rollSetRepository.findAllByUser(userService.getCurrentUser());}

    public void saveImage(AbstractProduct product) {
        try {
            product.setImage(product.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RollSet getRollSet(RollSetForm form) {
        RollSet rollSet = new RollSet();
        rollSet.setName(form.getName());
        rollSet.setPrice(form.getPrice());
        rollSet.setDescription(form.getDescription());
        rollSet.setFile(form.getFile());
        ArrayList<Roll> finalArray = new ArrayList<>();
        for (Long id : form.getId()){
            for (int i=1 ; i <=form.getQuantity().size(); i++) {
                finalArray.add(findRollById(id));
            }
        }
        rollSet.setRolls(finalArray);
        rollSet.setWeight(rollSet.getFinalWeight());
        rollSet.setUser(userService.getCurrentUser());
        return rollSet;
    }

}
